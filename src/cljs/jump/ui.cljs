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

; dimensions need to be available in system for camera tracking
(defonce canvas (.getElementById js/document "screen"))
(defonce screen (.getContext canvas "2d"))

(defn clear-screen!
  []
  (.clearRect screen 0 0 (.-width canvas) (.-height canvas)))

(defn renderable?
  [entity]
  (ent/has-attr entity :renderable))

(defn draw
  [entity]
  (when (renderable? entity)
    (let [{:keys [x y]} (ent/attr entity :position)
          {:keys [width height]} (ent/attr entity :renderable)]
      (.fillRect screen x y width height))))

(defn render
  [world]
  (when world
    (clear-screen!)
    (doseq [entity world]
      (draw entity))))
