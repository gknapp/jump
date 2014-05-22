(ns jump.ui
  (:require [jump.entity :as ent]))

(def request-frame (or (.-requestAnimationFrame js/window)
                       (.-webkitRequestAnimationFrame js/window)
                       (.-mozRequestAnimationFrame js/window)
                       (.-msRequestAnimationFrame js/window)))

;; map sprites to game entities
;; :sprite {:image (image "sprites/player.png") :top 94 :left 116 :height 35 :width 37}}})

#_(defn image
  [src]
  (let [img (new js/Image)]
    (set! (.-src img) src)
    img))

(defonce canvas (.getElementById js/document "screen"))
(defonce screen {:context (.getContext canvas "2d")
                 :height (.-height canvas)
                 :width  (.-width canvas)})

(defn draw
  [entity screen]
  (let [ctx (:context screen)
        {:keys [x y]} (ent/trait :position entity)
        {:keys [width height]} (ent/trait :renderable entity)]
    (.fillRect ctx x y width height)))

(defn render
  [entities]
  (let [renderable? #(map? (ent/trait :renderable %))
        entities (filter renderable? entities)]
    (when entities
      (doseq [entity entities]
        (draw entity screen)))))
