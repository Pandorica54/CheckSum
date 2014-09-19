import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Directory {

	static File dirPath;
	String[] output;

	public Directory(String directory) {
		Directory.dirPath = new File(directory);
	}

	public Directory(File directory) {
		Directory.dirPath = directory;
	}

	public Directory() {
		
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Files <directory>");
			System.exit(0);
		}
		dirPath = new File(args[0]);
		
		Directory face = new Directory();
		face.run();
	}

	public void run() {
		output = filesList();

		for (String s : output)
			System.out.println(s);
	}

	public static List<String> directoryList() {
		if (dirPath.isDirectory()) {
			String[] files = dirPath.list();
			List<String> output = new ArrayList<String>();
			for (String s : files) {
				if (!dirPath.getAbsolutePath().endsWith("\\"))
					output.add(dirPath.getAbsolutePath() + File.separator + s);
				else
					output.add(dirPath.getAbsolutePath() + s);
			}
			return output;
		}
		return null;
	}

	public String[] filesList() {
		List<String> directoryFiles = directoryList();
		List<String> files = new ArrayList<String>(directoryFiles);

		for (String s : directoryFiles) {
			File file = new File(s);
			if (file.isDirectory())
				files.remove(s);
		}
		String[] output = files.toArray(new String[files.size()]);
		return output;
	}
}
