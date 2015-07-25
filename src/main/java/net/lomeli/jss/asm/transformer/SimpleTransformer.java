package net.lomeli.jss.asm.transformer;

public abstract class SimpleTransformer {
    public abstract boolean rightClass(String name, String transformedName);

    public abstract byte[] patchClass(byte[] bytes);

    protected boolean matchesName(String name, String[] potential) {
        for (String mcp : potential) {
            if (mcp.equals(name))
                return true;
        }
        return false;
    }
}
