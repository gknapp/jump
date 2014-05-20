(ns jump.components)

(defn component [name & args]
  (merge {:type (keyword name)} args))

(defn position [x y]
  (component :position
             {:x x
              :y y}))

(defn control [input]
  (component :control
             {:input input}))

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
  (component :solid {:blocked false}))

(defn mortal []
  (component :mortal {:dead false}))
