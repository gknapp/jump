(ns jump.components)

; could be a macro
(defn component [name & args]
  {name (first args)})

(defn position [x y]
  (component :position
             {:x x
              :y y}))

(defn input []
  (component :input {}))

(defn renderable [w h]
  (component :renderable
             {:width w
              :height h}))

(defn walk [facing]
  ":blocked = :left | :right | false"
  (component :walk
             {:facing facing
              :blocked false}))

(defn jump []
  (component :jump
             {:on-ground true
              :blocked false}))

(defn gravity []
  (component :gravity {}))

(defn solid
  []
  (component :solid {}))

(defn ledge
  []
  (component :solid {:ledge true}))

(defn mortal []
  (component :mortal {:dead false}))

(defn player []
  (component :player {}))

(defn ai []
  (component :ai {}))
