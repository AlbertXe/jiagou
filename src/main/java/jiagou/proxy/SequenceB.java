package jiagou.proxy;

public class SequenceB {
	private static ThreadLocal<Integer> number = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};

	public int incr() {
		number.set(number.get() + 1);
		return number.get();
	}

	public static void main(String[] args) {
		SequenceB sequenceB = new SequenceB();
		new ThreadA(sequenceB).start();
		new ThreadA(sequenceB).start();
		new ThreadA(sequenceB).start();
	}

}

class ThreadA extends Thread {
	SequenceB sequenceB;

	public ThreadA(SequenceB sequenceB) {
		this.sequenceB = sequenceB;
	}

	@Override
	public void run() {
		for (int i = 0; i < 3; i++) {
			System.out.println(Thread.currentThread().getName() + "====" + sequenceB.incr());
		}
	}
}

