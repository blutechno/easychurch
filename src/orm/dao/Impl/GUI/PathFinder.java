/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package orm.dao.Impl.GUI;

/**
 *
 * @author jean pierre
 */
public class PathFinder {

    public String pathFinder(String relativeFile) {
        String filePath = getClass().getResource(relativeFile).getPath();
        filePath = filePath.substring(1);
        filePath = filePath.replace("%20", " ");
        return filePath;
    }
    
    public String getDbConfigFile(){
    return pathFinder("reports/dbConfig.properties");
    }
}
