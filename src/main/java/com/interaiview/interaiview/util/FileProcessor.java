package com.interaiview.interaiview.util;

import java.io.File;
import java.io.IOException;

public interface FileProcessor {
    String extractText(File file) throws IOException;
}
