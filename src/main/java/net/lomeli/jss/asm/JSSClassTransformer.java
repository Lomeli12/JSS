package net.lomeli.jss.asm;

import com.google.common.collect.Lists;

import java.util.List;

import net.minecraft.launchwrapper.IClassTransformer;

import net.lomeli.jss.asm.transformer.SimpleTransformer;
import net.lomeli.jss.asm.transformer.SlotCraftingTransformer;

public class JSSClassTransformer implements IClassTransformer {
    private List<SimpleTransformer> transformerList;

    public JSSClassTransformer() {
        transformerList = Lists.newArrayList();
        transformerList.add(new SlotCraftingTransformer());
    }

    @Override
    public byte[] transform(String s, String s1, byte[] bytes) {
        if (!transformerList.isEmpty()) {
            for (SimpleTransformer transformer : transformerList) {
                if (transformer != null && transformer.rightClass(s, s1))
                    return transformer.patchClass(bytes);
            }
        }
        return bytes;
    }
}
