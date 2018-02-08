package com.abc.de.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Data formatter utility class to transform input object/data into a specified
 * type.
 * 
 * @author Shekhar Suman
 * @version 1.0
 * @since 2017-03-01
 *
 */
public final class DataFormatter {

	private DataFormatter() {
	}

	/**
	 * Returns the input string after trimming extra spaces or empty string in
	 * case input string is null.
	 * 
	 * @param value
	 *            user input
	 * @return trimmed string or empty string
	 */
	public static String safeTrim(String value) {
		if (value == null)
			return "";
		else
			return value.trim();
	}

	/**
	 * Uncompresses the input string using Base64.
	 * 
	 * @param zipText
	 *            compressed input
	 * @return uncompressed string
	 * @throws IOException
	 */
	public static String uncompress(String zipText) throws IOException {
		byte[] compressed = Base64.decodeBase64(zipText);
		return uncompress(compressed);
	}

	/**
	 * Uncompresses the input string using Base64.
	 * 
	 * @param compressed
	 *            compressed input
	 * @return uncompressed string
	 * @throws IOException
	 */
	private static String uncompress(byte[] compressed) throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
		GZIPInputStream gis = new GZIPInputStream(bis);
		BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		br.close();
		gis.close();
		bis.close();
		return sb.toString();
	}

	/**
	 * Serializes the input object using Kryo serialization.
	 * 
	 * @param object
	 *            input object
	 * @param kryo
	 *            Kryo serialization object
	 * @return serialized object
	 * @throws IOException
	 */
	public static byte[] serializeWithKryo(Object object, Kryo kryo) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Output output = new Output(baos);

		kryo.writeObject(output, object);
		output.close();
		baos.close();
		return baos.toByteArray();
	}

	/**
	 * De-serializes the input object using Kryo.
	 * 
	 * @param bytes
	 *            input byte array
	 * @param t
	 *            Type of input object to be de-serialized into
	 * @param kryo
	 *            Kryo serialization object
	 * @return de-serialized object.
	 * @throws IOException
	 * @throws IOException
	 */
	public static <T> T deserializeWithKryo(byte[] bytes, Class<T> t, Kryo kryo) throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		Input input = new Input(bais);
		T bm = kryo.readObject(input, t);
		input.close();
		bais.close();
		return bm;
	}
}
