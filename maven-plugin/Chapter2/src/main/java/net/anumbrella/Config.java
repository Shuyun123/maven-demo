package net.anumbrella;

/**
 * @auther anumbrella
 */

public class Config {

    private String pkg;

    /**
     * entity的包名
     */
    private String entityPkg = "entity";


    public String getPkg() {
        return pkg;
    }

    public void setPkg(String pkg) {
        this.pkg = pkg;
    }

    public String getEntityPkg() {
        return entityPkg;
    }

    public void setEntityPkg(String entityPkg) {
        this.entityPkg = entityPkg;
    }
}
