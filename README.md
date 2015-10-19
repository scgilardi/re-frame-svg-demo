# re-frame-svg-demo

A [re-frame](https://github.com/Day8/re-frame) application that allows
drawing of svg rects onto a drawing area.

For [re-frame issue \#125](https://github.com/Day8/re-frame/issues/125), drag out some
rectangles and watch carefully at the end of each as drag as the item
transitions from "the item being created" to "one of the items that's
been saved. You'll see flicker in many instances. Drawing a few small
rectangles and then another one that overlaps all of them often makes
the flicker more noticeable when it happens.

## Development Mode

### Run application:

```
lein fw
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build

```
lein release
```
