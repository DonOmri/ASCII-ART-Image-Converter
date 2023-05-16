package image;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
class ImageIterableProperty<T> implements Iterable<T> {
    private final Image img;
    private final int size;
    private final BiFunction<Integer, Integer, T> propertySupplier;

    public ImageIterableProperty(
            Image img,
            BiFunction<Integer, Integer, T> propertySupplier, int size) {
        this.img = img;
        this.propertySupplier = propertySupplier;
        this.size = size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int x = 0, y = 0;

            @Override
            public boolean hasNext() {
                return y < img.getHeight();
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                var next = propertySupplier.apply(x, y);
                x += size;
                if (x >= img.getWidth()) {
                    x = 0;
                    y += size;
                }
                return next;
            }
        };
    }
}
