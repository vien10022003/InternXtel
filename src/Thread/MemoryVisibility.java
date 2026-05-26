package Thread;

// VAN DE 3: MEMORY VISIBILITY
// Thread A sua bien, Thread B khong thay (vi cache)
// CPU cache dung rieng cho tung core -> Thread o core khac khong thay cai changes

public class MemoryVisibility {
    
    // CACH SAI: Khong volatile hoac synchronized
    static class VisibilityProblem {
        private boolean flag = false;  // Bien chung
        
        public void setFlag() {
            flag = true;
            System.out.println("Writer: dat flag = true");
        }
        
        public void checkFlag() {
            while (!flag) {
                // Vong lap vo han (hoac rat lau) vi flag o trong cache, khong thay thay doi
            }
            System.out.println("Reader: thay flag = true!");
        }
    }
    
    // CACH DUNG 1: Dung volatile
    static class VolatileFix {
        private volatile boolean flag = false;  // Volatile buoc cap nhat cache
        
        public void setFlag() {
            flag = true;
            System.out.println("Writer: dat flag = true");
        }
        
        public void checkFlag() {
            while (!flag) {
                // Volatile dam bao doc tu memory chinh, khong cache
            }
            System.out.println("Reader: thay flag = true!");
        }
    }
    
    // CACH DUNG 2: Dung synchronized
    static class SynchronizedFix {
        private boolean flag = false;
        
        public synchronized void setFlag() {
            flag = true;
            System.out.println("Writer: dat flag = true");
        }
        
        public synchronized void checkFlag() {
            while (!flag) {
                // Synchronized dam bao visibility (enter/exit lock)
            }
            System.out.println("Reader: thay flag = true!");
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== MEMORY VISIBILITY ===\n");
        
        // Test 1: Memory visibility problem (khong volatile)
        System.out.println("Test 1: Khong volatile (co the treo vai giay)");
        VisibilityProblem vp = new VisibilityProblem();
        
        Thread writer1 = new Thread(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            vp.setFlag();
        });
        
        Thread reader1 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            vp.checkFlag();
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("Thoi gian: " + elapsed + "ms");
        });
        
        reader1.start();  // Bat dau doc trc
        writer1.start();  // Sau do writer ghi
        
        reader1.join();
        writer1.join();
        
        System.out.println("Nhan xet: Co the reader khong thay flag = true ngay lap tuc!\n");
        
        // Test 2: Fix voi volatile
        System.out.println("Test 2: Dung volatile");
        VolatileFix vf = new VolatileFix();
        
        Thread writer2 = new Thread(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            vf.setFlag();
        });
        
        Thread reader2 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            vf.checkFlag();
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("Thoi gian: " + elapsed + "ms");
        });
        
        reader2.start();
        writer2.start();
        
        reader2.join();
        writer2.join();
        
        System.out.println("Nhan xet: Volatile dam bao reader thay flag = true ngay!\n");
        
        // Test 3: Fix voi synchronized
        System.out.println("Test 3: Dung synchronized");
        SynchronizedFix sf = new SynchronizedFix();
        
        Thread writer3 = new Thread(() -> {
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            sf.setFlag();
        });
        
        Thread reader3 = new Thread(() -> {
            long startTime = System.currentTimeMillis();
            sf.checkFlag();
            long elapsed = System.currentTimeMillis() - startTime;
            System.out.println("Thoi gian: " + elapsed + "ms");
        });
        
        reader3.start();
        writer3.start();
        
        reader3.join();
        writer3.join();
        
        System.out.println("Nhan xet: Synchronized cung dam bao memory visibility!\n");
        
        // So sanh: volatile vs synchronized
        System.out.println("So sanh:");
        System.out.println("- volatile: Chi dam bao visibility, khong dam bao atomic");
        System.out.println("- synchronized: Dam bao visibility va atomic, nhung cham hon");
    }
}
