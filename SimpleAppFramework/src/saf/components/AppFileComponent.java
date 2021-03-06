package saf.components;

import java.io.File;
import java.io.IOException;

/**
 * This interface provides the structure for file components in
 * our applications. Note that by doing so we make it possible
 * for customly provided descendent classes to have their methods
 * called from this framework.
 * 
 * @author Richard McKenna
 * @author ?
 * @version 1.0
 */
public interface AppFileComponent {

    /**
     *
     * @param data
     * @param filePath
     * @throws IOException
     */
    public void saveData(AppDataComponent data, String filePath) throws IOException;

    /**
     *
     * @param data
     * @param filePath
     * @throws IOException
     */
    public void loadData(AppDataComponent data, String filePath) throws IOException;

    /**
     *
     * @param data
     * @param filePath
     * @throws IOException
     */
    public void exportData(AppDataComponent data, String filePath) throws IOException;

    /**
     *
     * @param data
     * @param filePath
     * @throws IOException
     */
    public void newData(AppDataComponent data, String mapName, String filePath, File directory) throws IOException;
}
