package com.github.minecraftschurlimods.arsmagicalegacy.test;

import com.github.minecraftschurlimods.arsmagicalegacy.ArsMagicaAPIImpl;
import com.github.minecraftschurlimods.arsmagicalegacy.api.ArsMagicaAPI;
import net.minecraft.gametest.framework.GameTest;
import net.neoforged.testframework.annotation.ForEachTest;
import net.neoforged.testframework.annotation.TestHolder;
import net.neoforged.testframework.gametest.EmptyTemplate;
import net.neoforged.testframework.gametest.ExtendedGameTestHelper;

@ForEachTest(groups = ArsMagicaAPI.MOD_ID + ".api")
public class ArsMagicaAPITest {
    @GameTest
    @EmptyTemplate
    @TestHolder(description = "Test that the implementation of the ArsMagicaAPI is available and of the correct class")
    public static void testApiNotDummy(ExtendedGameTestHelper helper) {
        helper.assertTrue(ArsMagicaAPI.get().getClass() == ArsMagicaAPIImpl.class, "Wrong Implementation of ArsMagicaAPI!");
        helper.succeed();
    }
}
