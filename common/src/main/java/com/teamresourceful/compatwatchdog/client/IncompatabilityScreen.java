package com.teamresourceful.compatwatchdog.client;

import com.teamresourceful.compatwatchdog.CompatWatchDog;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Environment(EnvType.CLIENT)
public class IncompatabilityScreen extends Screen {

	private static final MutableComponent OPEN_FOLDER = Component.literal("Open Mods Folder");
	private static final MutableComponent QUIT = Component.literal("Quit Game");
	private final List<SplitLineEntry> lines;
	private IncompatabilityScreen.SimpleTextList theList;

	public IncompatabilityScreen(Component component, List<SplitLineEntry> list) {
		super(component);
		this.lines = list;
	}

	@Override
	protected void init() {
		this.theList = new IncompatabilityScreen.SimpleTextList(this.minecraft, this.lines);
		this.theList.setRenderBackground(false);
		this.addWidget(this.theList);
		int i = this.width / 2 - 150 - 5;
		int j = this.width / 2 + 5;
		int k = this.height - 20 - 8;
		this.addRenderableWidget(Button.builder(QUIT, (button) -> Minecraft.getInstance().stop()).bounds(j, k, 150, 20).build());
		this.addRenderableWidget(Button.builder(OPEN_FOLDER, (button) -> CompatWatchDog.openModsFolder()).bounds(i, k, 150, 20).build());
	}



	@Override
	public void render(@NotNull GuiGraphics graphics, int i, int j, float f) {
		this.renderBackground(graphics);
		this.theList.render(graphics, i, j, f);
		graphics.drawCenteredString(this.font, this.title, this.width / 2, 16, 16777215);
		super.render(graphics, i, j, f);
	}

	@Environment(EnvType.CLIENT)
	public class SimpleTextList extends ObjectSelectionList<SimpleTextList.Entry> {
		public SimpleTextList(Minecraft minecraft, List<SplitLineEntry> list) {
			super(minecraft, IncompatabilityScreen.this.width, IncompatabilityScreen.this.height, 40, IncompatabilityScreen.this.height - 40, 18);

			for (SplitLineEntry splitLineEntry : list) {
				this.addEntry(new Entry(splitLineEntry));
			}
		}

		@Override
		public int getRowWidth() {
			return 320;
		}

		@Override
		protected int getScrollbarPosition() {
			return this.getRowRight() - 2;
		}

		@Environment(EnvType.CLIENT)
		public class Entry extends ObjectSelectionList.Entry<IncompatabilityScreen.SimpleTextList.Entry> {
			private final SplitLineEntry line;

			public Entry(SplitLineEntry splitLineEntry) {
				this.line = splitLineEntry;
			}

			@Override
			public void render(@NotNull GuiGraphics graphics, int i, int j, int k, int l, int m, int n, int o, boolean bl, float f) {
				int p = k + 1 + (this.line.index() > 0L ? 16 : 0);
				int q = j + (m - 9) / 2 + 1;
				graphics.drawString(IncompatabilityScreen.this.font, this.line.contents(), p, q, -1);
			}

			@Override
			public @NotNull Component getNarration() {
				return Component.translatable("narrator.select", this.line.original());
			}

			@Override
			public boolean mouseClicked(double d, double e, int i) {
				if (i == 0) {
					SimpleTextList.this.setSelected(this);
					return true;
				}
				return false;
			}
		}
	}
}