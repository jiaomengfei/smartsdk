package secure;

import java.util.Random;


public class RandomUtil {


	public static String genRandom(int length) {
			StringBuffer buffer = new StringBuffer();
			Random r = new Random();
			int i = 0;
	
			while (i < length) {
				int c = r.nextInt(122);
				if (((48 <= c) && (c <= 57)) || ((65 <= c) && (c <= 90))
						|| ((97 <= c) && (c <= 122))) {
					buffer.append((char) c);
					++i;
				}
			}
			return buffer.toString();
		}
}

