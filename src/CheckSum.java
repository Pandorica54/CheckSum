import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSum {

	static String[] input = new String[2];

	public CheckSum(String fileOrDirectory) {
		CheckSum.input[0] = fileOrDirectory;
	}

	public CheckSum(String file1, String file2) {
		CheckSum.input[0] = file1;
		CheckSum.input[1] = file2;
	}

	public CheckSum(File fileOrDirectory) {
		CheckSum.input[0] = fileOrDirectory.getAbsolutePath();
	}

	public CheckSum(File file1, File file2) {
		CheckSum.input[0] = file1.getAbsolutePath();
		CheckSum.input[1] = file2.getAbsolutePath();
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Usage: java CheckSum <directory>");
			System.out.println("Usage: java CheckSum <file>");
			System.out.println("Usage: java CheckSum <file> <file>");
			System.exit(0);
		}
		try {
			if (args.length == 1) {
				File file = new File(args[0]);
				if (file.isDirectory()) {

				} else if (file.isFile()) {
					input[0] = file.getAbsolutePath();
					System.out.println(MD5CheckSum());
					System.out.println(SHA1CheckSum());
				} else {
					System.out.println("You have not entered a directory or a file.");
					System.exit(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String MD5CheckSum() throws NoSuchAlgorithmException, IOException {
		return hash(input[0], "MD5");
	}
	
	public static String SHA1CheckSum() throws NoSuchAlgorithmException, IOException{
		return hash(input[0],"SHA-1");
	}

	private static String hash(String inputFile, String algorithm) throws IOException, NoSuchAlgorithmException {
		File file = new File(inputFile);
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		if (file.isDirectory() || file.isFile()) {
			byte[] data = Files.readAllBytes(file.getAbsoluteFile().toPath());
			digest.update(data);
			byte[] hashed = digest.digest();
			return convertByteToHex(hashed); 
		}
		return null;
	}

	private static String convertByteToHex(byte[] bytes) {
		StringBuffer stringBuffer = new StringBuffer();
		for(int i = 0; i < bytes.length; i++)
			stringBuffer.append(Integer.toString((bytes[i] & 0xff) + 0x100,16).substring(1));
		return stringBuffer.toString();
	}
}
