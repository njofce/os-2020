package av1.grupa_2;

import java.io.File;
import java.io.FileFilter;

public class TxtWritePermissionFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        return pathname.getAbsolutePath().endsWith(".txt") && pathname.canWrite();
    }
}
