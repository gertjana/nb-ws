package net.addictivesoftware.nbws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Routes {
    private List<Path> paths = new ArrayList<Path>();

    public Routes() {
        this("/routes");
    }

    public Routes(String routeFile) {
        InputStream is = this.getClass().getResourceAsStream(routeFile);
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null)   {
                if (!(line.startsWith("#") || line.trim().equals(""))) {
                    paths.add(Path.parse(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
