package net.addictivesoftware.nbws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Routes {
    private List<Path> paths = new ArrayList<Path>();

    public List<Path> parse() throws IOException {
        List<Path> result = new ArrayList<Path>();
        InputStream is = this.getClass().getResourceAsStream("routes");
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null)   {
                if (!line.startsWith("#")) {
                    result.add(Path.parse(line));
                }
            }
        } finally {
            is.close();
        }
        
        return result;
    }
    
    public Path getPathForUri(String method, String uri) {
        for (Path path : paths) {
            if (path.match(method, uri)) {
                return path;
            }
        }
        return null;
    }
}
