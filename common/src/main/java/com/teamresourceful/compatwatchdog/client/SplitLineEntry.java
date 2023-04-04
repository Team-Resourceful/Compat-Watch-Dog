package com.teamresourceful.compatwatchdog.client;

import com.google.common.collect.Streams;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.stream.Stream;

public record SplitLineEntry(FormattedCharSequence contents, long index, Component original) {

	public static Stream<SplitLineEntry> splitToWidth(Font font, Stream<Component> stream, int i) {
		return stream.flatMap((component) ->
				Streams.mapWithIndex(font.split(component, i).stream(),
						(formattedCharSequence, l) -> new SplitLineEntry(formattedCharSequence, l, component)
				)
		);
	}
}