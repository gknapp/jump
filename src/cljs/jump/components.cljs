(ns jump.components)

; could be a macro
(defn component [name & args]
  {name (first args)})

(defn position [x y]
  (component :position
             {:x x
              :y y}))

(defn input []
  (component :input {:blocked false}))

(defn renderable [w h]
  (component :renderable
             {:width w
              :height h}))

(defn walk [facing]
  (component :walk
             {:velocity 0.0
              :facing facing}))

(defn jump []
  (component :jump
             {:velocity 0.0
              :on-ground true}))

(defn gravity []
  (component :gravity {}))

(defn solid
  []
  (component :solid {}))

(defn ledge
  []
  (component :solid {:ledge true}))

(defn player []
  (component :player {}))

(defn ai []
  (component :ai {}))
