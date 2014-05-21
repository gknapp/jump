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

(defn player []
  (component :player {:blocked false}))

(defn ai []
  (component :ai {}))

(defn renderable [w h]
  (component :renderable
             {:width w
              :height h}))

(defn walk [step facing]
  (component :walk
             {:step step
              :facing facing}))

(defn gravity [w]
  (component :gravity
             {:weight w}))

(defn jump [h]
  (component :jump
             {:height h
              :falling false
              :ground true}))

(defn solid []
  (component :solid {}))

(defn mortal []
  (component :mortal {:dead false}))
