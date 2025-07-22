package com.ndl.trustviec.common.type;

public enum FolderEnum {
    POST,
    LOGO;

    public static boolean contains(String value) {
        for (FolderEnum folder : FolderEnum.values()) {
            if (folder.name().equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }
}
