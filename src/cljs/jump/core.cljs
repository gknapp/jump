(ns jump.core)

(enable-console-print!)

(defn screen
  ([]
     (screen "screen"))
  ([elem-id]
     (let [screen (.getElementById js/document elem-id)]
       {:context (.getContext screen "2d")
        :height (.-height screen)
        :width (.-width screen)})))

(defn draw-sprite
  [player screen]
  (let [ctx (:context screen)
        sprite (:image player)
        t (:top player)
        l (:left player)
        w (:width player)
        h (:height player)]
    (println ctx sprite l t w h 10 10 w h)
    (.drawImage ctx sprite l t w h 10 10 w h)))

(defn image
  [src]
  (let [img (new js/Image)]
    (set! (.-src img) src)
    img))

(def player
  {:image (image "sprites/player.png")
   :top 94
   :left 167
   :height 35
   :width 37
   :facing :right
   :falling false
   :jumping false})

(set! (.-onload js/window)
      (do
        (println "Window loaded")
        (println "player" player)
        (println "screen" (screen))
        (draw-sprite player (screen))))
