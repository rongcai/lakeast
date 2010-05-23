package org.lakeast.common;

/**
 * Utility Class (private contructor) used to format array contents in (or to) a
 * StringBuffer.
 * 
 */
public final class ToStringBuffer {

	/** Can't Create an instance of listToStringBuffer */
	private ToStringBuffer() {
	}

	/**
	 * Returns new StringBuffer with formatted contents of a double array.
	 * Accepts a pre, separator and post <code>String</code> to use when
	 * formatting the contents.
	 * <p>
	 * Given <CODE>double[] array = {1.0, 2.0}</CODE>,
	 * <p>
	 * <CODE>ToStringBuffer.list(array,"double[] = {",", ","}")</CODE> returns a
	 * StringBuffer containing:
	 * <p>
	 * "<CODE>double[] = {1.0, 2.0}</CODE>".
	 * 
	 * @param array
	 *            the array of <CODE>double</CODE>s.
	 * @param pre
	 *            String to prefix the array contents with.
	 * @param separator
	 *            String to insert between each item in the array.
	 * @param post
	 *            String to add after the array contents.
	 * @return a new StringBuffer with the formatted array contents.
	 */
	public static final StringBuffer list(double[] array, String pre,
			String separator, String post) {
		StringBuffer s = new StringBuffer();
		s.append(pre);
		s.append(array[0]);
		for (int i = 1, size = array.length; i < size; i++) {
			s.append(separator);
			s.append(array[i]);
		}
		s.append(post);
		return s;
	}

	/**
	 * Appends to referenced StringBuffer (arg 5) the formatted contents of a
	 * double array. Accepts a pre, separator and post <code>String</code> to
	 * use when formatting the contents.
	 * <p>
	 * Given <CODE>double[] array = {1.0, 2.0}</CODE> and
	 * <p>
	 * <CODE>StringBuffer buffer = "My array: "</CODE>,
	 * <p>
	 * method
	 * <CODE>ToStringBuffer.list(array,"double[] = {",", ","}",buffer)</CODE>
	 * will append to <CODE>buffer</CODE> yeilding:
	 * <p>
	 * "<CODE>My array: double[] = {1.0, 2.0}</CODE>".
	 * 
	 * @param array
	 *            the array of <CODE>double</CODE>s.
	 * @param pre
	 *            String to prefix the array contents with.
	 * @param separator
	 *            String to insert between each item in the array.
	 * @param post
	 *            String to add after the array contents.
	 * @param buffer
	 *            the <CODE>StringBuffer</CODE> to be appended to.
	 */
	public static final void list(double[] array, String pre, String separator,
			String post, StringBuffer buffer) {
		buffer.append(pre);
		buffer.append(array[0]);
		for (int i = 1, size = array.length; i < size; i++) {
			buffer.append(separator);
			buffer.append(array[i]);
		}
		buffer.append(post);
	}

	public static final void list(byte[] array, String pre, String separator,
			String post, StringBuffer buffer) {
		buffer.append(pre);
		buffer.append(array[0]);
		for (int i = 1, size = array.length; i < size; i++) {
			buffer.append(separator);
			buffer.append(array[i]);
		}
		buffer.append(post);
	}

	public static final void list(int[] array, String pre, String separator,
			String post, StringBuffer buffer) {
		buffer.append(pre);
		buffer.append(array[0]);
		for (int i = 1, size = array.length; i < size; i++) {
			buffer.append(separator);
			buffer.append(array[i]);
		}
		buffer.append(post);
	}
}
