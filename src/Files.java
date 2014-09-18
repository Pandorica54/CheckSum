import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Files {

	static File dirPath;

	public Files(String directory) {
		Files.dirPath = new File(directory);
	}

	public Files(File directory) {
		Files.dirPath = directory;
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Files <directory>");
			System.exit(0);
		}

		dirPath = new File(args[0]);
		List<String> output = filesList();

		for (String s : output)
			System.out.println(s);
	}

	public static List<String> directoryList() {
		if (dirPath.isDirectory()) {
			String[] files = dirPath.list();
			List<String> output = new ArrayList<String>();
			for (String s : files)
				output.add(dirPath.getAbsolutePath() + File.separator + s);
			for(String s : output)
				s.replace(target, replacement)
			return output;
		}
		return null;
	}

	public static List<String> filesList() {
		List<String> files = directoryList();
		List<String> output = new ArrayList<String>(files);

		for (String s : files) {
			File file = new File(s);
			if (file.isDirectory())
				output.remove(s);
		}
		return output;
	}
}
