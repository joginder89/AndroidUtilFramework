package in.jogindersharma.jsutilsframework.colorpicker.builder;


import in.jogindersharma.jsutilsframework.colorpicker.ColorPickerView;
import in.jogindersharma.jsutilsframework.colorpicker.renderer.ColorWheelRenderer;
import in.jogindersharma.jsutilsframework.colorpicker.renderer.FlowerColorWheelRenderer;
import in.jogindersharma.jsutilsframework.colorpicker.renderer.SimpleColorWheelRenderer;

public class ColorWheelRendererBuilder {
	public static ColorWheelRenderer getRenderer(ColorPickerView.WHEEL_TYPE wheelType) {
		switch (wheelType) {
			case CIRCLE:
				return new SimpleColorWheelRenderer();
			case FLOWER:
				return new FlowerColorWheelRenderer();
		}
		throw new IllegalArgumentException("wrong WHEEL_TYPE");
	}
}