(ns jump.ui)

(def request-frame (or (.-requestAnimationFrame js/window)
                       (.-webkitRequestAnimationFrame js/window)
                       (.-mozRequestAnimationFrame js/window)
                       (.-msRequestAnimationFrame js/window)))

;; map sprites to game entities
;; :sprite {:image (image "sprites/player.png") :top 94 :left 116 :height 35 :width 37}}})

(defn image
  [src]
  (let [img (new js/Image)]
    (set! (.-src img) src)
    img))

(defn draw
  [entity screen]
  #_(.fillRect screen x y w h)
  (println entity)
  (println screen))

(defonce canvas (.getElementById js/document "screen"))
(defonce screen {:context (.getContext canvas "2d")
                 :height (.-height canvas)
                 :width  (.-width canvas)})

(defn renderable?
  ; {:id :player :traits [... {:type :renderable} ...]}
  [{:keys [traits]}]
  (some #(= (:type %) :renderable) traits))

(defn render
  [game]
  (let [;_ (println (:entities game))
        entities (->> game
                      :entities
                      (filter renderable?))]
    (map #(draw % (:context screen)) entities)))
