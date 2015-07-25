package net.lomeli.jss.asm.transformer;

import org.apache.logging.log4j.Level;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.*;

import cpw.mods.fml.common.FMLLog;

import static org.objectweb.asm.Opcodes.*;

/*
 * Note to self, make a PR to add this event to forge so I don't have to deal with coremod crap
 */
public class SlotCraftingTransformer extends SimpleTransformer {
    private final String[] classNames = new String[]{"net.minecraft.inventory.SlotCrafting", "aax"};
    private final String[] pickMethod = new String[]{"onPickupFromSlot", "func_82870_a", "a"};
    private final String pickMethodDescript = "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)V";
    private final String[] craftingMatrixField = new String[]{"craftMatrix", "field_75239_a", "a"};

    @Override
    public boolean rightClass(String name, String transformedName) {
        return matchesName(name, classNames) || matchesName(transformedName, classNames);
    }

    @Override
    public byte[] patchClass(byte[] bytes) {
        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);

        FMLLog.log("JSS", Level.INFO, "Found SlotCrafting class, attempting to inject event");
        FMLLog.log("JSS", Level.WARN, "Stuff can go really wrong here, just a heads up.");

        MethodNode onPickFromSlot = null;

        for (MethodNode node : classNode.methods) {
            if (matchesName(node.name, pickMethod) && node.desc.equals(pickMethodDescript)) {
                onPickFromSlot = node;
                break;
            }
        }

        if (onPickFromSlot != null) {
            FMLLog.log("JSS", Level.INFO, "Found onPickupFromSlot method!");
            for (int i = 0; i < onPickFromSlot.instructions.size(); i++) {
                AbstractInsnNode abstractNode = onPickFromSlot.instructions.get(i);
                if (abstractNode instanceof FieldInsnNode) {
                    FieldInsnNode fieldNode = (FieldInsnNode) abstractNode;
                    if (matchesName(fieldNode.name, craftingMatrixField)) {
                        FMLLog.log("JSS", Level.INFO, "Found the right spot, attempting to insert hook!");
                        LabelNode labelNode = new LabelNode();
                        InsnList insertList = new InsnList();

                        insertList.add(new VarInsnNode(ALOAD, 1));
                        insertList.add(new VarInsnNode(ALOAD, 2));
                        insertList.add(new MethodInsnNode(INVOKESTATIC, "net/lomeli/jss/asm/JSSHooks", "onItemCraftEvent", "(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)Z", false));
                        insertList.add(new JumpInsnNode(IFEQ, labelNode));
                        insertList.add(new InsnNode(RETURN));
                        insertList.add(labelNode);

                        onPickFromSlot.instructions.insertBefore(onPickFromSlot.instructions.get(i + 1), insertList);
                        break;
                    }
                }
            }
        }

        FMLLog.log("JSS", Level.INFO, "Writing changes to SlotCrafting! We're almost there!");
        ClassWriter writer = new ClassWriter(0);
        classNode.accept(writer);
        FMLLog.log("JSS", Level.INFO, "Yay! We made it! Hopefully nothing went wrong!");
        return writer.toByteArray();
    }
}
