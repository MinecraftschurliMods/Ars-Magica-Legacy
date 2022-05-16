package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaAPIImpl;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

@PrefixGameTestTemplate(false)
@GameTestHolder(ArsMagicaAPI.MOD_ID)
public class ArsMagicaAPITest {
    @GameTest(template = "empty")
    public void testApiNotDummy(GameTestHelper helper) {
        if (ArsMagicaAPI.get() instanceof ArsMagicaAPIImpl) {
            helper.succeed();
        } else {
            helper.fail("Wrong Implementation of ArsMagicaAPI.IArsMagicaAPI!");
        }
    }
}
