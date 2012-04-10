
import java.io {InputStream, IOException, BufferedReader, InputStreamReader}
import java.util {List, ArrayList}


shared class Route(String file="/routes") {
	shared String file = file;
	ArrayList<Path> paths = ArrayList<Path>();

	/*
	InputStream? inputstream = this.resourceAsStream(file);
	
	try {
		BufferedReader br = BufferedReader(InputStreamReader(inputstream));

		while (nonempty line=br.readLine()) {
			if (!line.startsWith("#") && !line.trimmed.empty) {
				paths.add(Path(line));
			}
		}
	} finally {
		if (nonempty inputstream) {
			inputstream.close();
		}
	}*/

	shared Path? pathForUri(String method, String uri) {
		variable Path? path := null;	
		
		for (Integer i in 0..paths.size()-1) {
			path := paths.get(i);
			//if (exists path.match(method, uri)) {
            //    break;
            //}
		} 
		return path;
	}
} 