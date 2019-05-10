
package com.itmayiedu;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Res {
	public String userName;
	public String sex;
	public boolean flag = false;
	Lock lock = new ReentrantLock();
}

class InputThread extends Thread {
	private Res res;
	Condition newCondition;
	public InputThread(Res res,	Condition newCondition) {
		this.res = res;
		this.newCondition=newCondition;
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			try {
				res.lock.lock();
				if (res.flag) {
					try {
						newCondition.await();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				if (count == 0) {
					res.userName = "ÓàÊ¤¾ü";
					res.sex = "ÄÐ";
				} else {
					res.userName = "Ð¡ºì";
					res.sex = "Å®";
				}
				count = (count + 1) % 2;
				res.flag = true;
				newCondition.signal();
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				res.lock.unlock();
			}
		}
	}
}

class OutThrad extends Thread {
	private Res res;
	private Condition newCondition;
	public OutThrad(Res res,Condition newCondition) {
		this.res = res;
		this.newCondition=newCondition;
	}

	@Override
	public void run() {
		while (true) {
			try {
				res.lock.lock();
				if (!res.flag) {
					try {
						newCondition.await();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				System.out.println(res.userName + "," + res.sex);
				res.flag = false;
				newCondition.signal();
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				res.lock.unlock();
			}
		}

	}
}

public class ThreadDemo01 {

	public static void main(String[] args) {
		Res res = new Res();
		Condition newCondition = res.lock.newCondition();
		InputThread inputThread = new InputThread(res,newCondition);
		OutThrad outThrad = new OutThrad(res,newCondition);
		inputThread.start();
		outThrad.start();
	}

}
