package core;
import java.util.Random;


public class RNG {

	private Random m_random;

	public RNG() {
		this.m_random = new Random(System.currentTimeMillis());
	}

	/**
	 * A random generater that generate an integer given boundary of min and max.
	 * Always return 0 if max < min.
	 * @param min
	 * @param max
	 * @return
	 */
	public int GetRandomInteger(int min, int max){
		if(max < min)
		{
			return 0;
		}
		if (min == max) {
			return this.m_random.nextInt(max + 1) + min;
		}
		else {
			return this.m_random.nextInt((max - min) + 1) + min;
		}
		
	}
}