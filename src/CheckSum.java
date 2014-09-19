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
				
				fileOrDirectory(args);
				
			} else if (args.length == 2) {

				fileComparison(args);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void fileOrDirectory(String[] args) throws NoSuchAlgorithmException, IOException {
		File file = new File(args[0]);
		
		if (file.isDirectory()) {
			
			Directory dir = new Directory(file);
			String[] fileList = dir.filesList();
			
			for (int i = 0; i < fileList.length; i++) {
				
				input[0] = fileList[i];
				System.out.println(MD5CheckSum());
				System.out.println(SHA1CheckSum());
				
			}
		} else if (file.isFile()) {
			input[0] = file.getAbsolutePath();
			
			System.out.println(MD5CheckSum());
			System.out.println(SHA1CheckSum());
			
		} else {
			System.out.println("You have not entered a directory or a file.");
			System.exit(0);
		}
	}

	private static void fileComparison(String[] args) throws NoSuchAlgorithmException, IOException {
		File file1 = new File(args[0]);
		File file2 = new File(args[1]);
		
		if (file1.isFile() && file2.isFile()) {
			
			input[0] = args[0];
			input[1] = args[1];
			
			if (comparison())
				System.out.println("The files are identical");
			else
				System.out.println("The files are different");
		}
	}

	public static String MD5CheckSum() throws NoSuchAlgorithmException, IOException {
		String returnable = "";
		returnable += "MD5: " + hash(input[0], "MD5");
		return returnable;
	}

	public static String SHA1CheckSum() throws NoSuchAlgorithmException, IOException {
		String returnable = "";
		returnable += "SHA-1: " + hash(input[0], "SHA-1");
		return returnable;
	}

	public static boolean comparison() throws NoSuchAlgorithmException, IOException {
		String MD5_1, SHA1_1, MD5_2, SHA1_2;
		boolean MD5 = false, SHA1 = false;

		MD5_1 = hash(input[0], "MD5");
		MD5_2 = hash(input[1], "MD5");
		SHA1_1 = hash(input[0], "SHA1");
		SHA1_2 = hash(input[1], "SHA1");

		if (MD5_1.equals(MD5_2))
			MD5 = true;
		if (SHA1_1.equals(SHA1_2))
			SHA1 = true;

		if (SHA1 == true && MD5 == true)
			return true;

		return false;
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
		
		for (int i = 0; i < bytes.length; i++)
			stringBuffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		
		return stringBuffer.toString();
	}
}