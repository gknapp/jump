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
        w (:width player)
        h (:height player)
        o-top 94
        o-left 167
        o-height (+ o-top h)
        o-width (+ o-left w)]
    (println o-top o-left w h)
    (.drawImage ctx sprite 10 10)))

(defn image
  [src]
  (let [img (new js/Image)]
    (set! (.-src img) src)
    img))

(def player
  {:image (image "sprites/player.png")
   :height 35
   :width 37
   :facing :right
   :falling false
   :jumping false})

(set! (.-onload js/window)
      (do
        (println "Window loaded")
        (draw-sprite player (screen))))
