package com.mycompany.mavenproject7;

import java.util.Scanner;

public class Mavenproject7 {
    
    private static final Object lock = new Object();
    private static volatile boolean running = true;
    
    public static void main(String[] args) {
        System.out.println("Practical task 1.14, Student Budrik Sophia, RIBO-04-22, Variant 4");
        
        Thread thread1 = new Thread(new Worker(0));  //создаем два потока
        Thread thread2 = new Thread(new Worker(1));

        thread1.start();
        thread2.start();
      
try (Scanner scanner = new Scanner(System.in)) {  // Считываем клавиши для остановки
            scanner.nextLine();
            running = false;
        }
    }

static class Worker implements Runnable {
        private final int threadNumber;

        Worker(int threadNumber) {
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            while (running) {
                synchronized (lock) {                   
                    System.out.println("Thread-" + threadNumber);   // Печатаем имя потока и ждем
                    try {
                        lock.notifyAll();
                        if (running) {
                            lock.wait();
                        }
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();    //обрабатываем исключение, если метод прерывается
                        System.out.println("Thread - " + threadNumber + " interrupted");
                    }
                }
            }
        }
    }
}
