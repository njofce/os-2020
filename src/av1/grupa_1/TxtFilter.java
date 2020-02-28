package av1.grupa_1;

import java.io.File;
import java.io.FilenameFilter;

public class TxtFilter implements FilenameFilter {

    @Override
    public boolean accept(File dir, String name) {
        return name.endsWith(".txt");
    }

}
