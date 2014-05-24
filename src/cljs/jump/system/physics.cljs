(ns jump.system.physics
  (:require [jump.entity :as ent]))

(def gravity 0.5) ; gravitational pull
(def step 10)     ; jump / walk amount per frame
(def height 60)   ; max jump height
(def weight 10)   ; gravitational pull

;; Actions

; walking

(defn walk?
  "is walk command?"
  [[cmd evt]]
  (#{:left :right} cmd))

(defn can-walk?
  "entity has a position, can walk and takes input commands"
  [entity]
  (and (ent/has-attr entity :position)
       (ent/has-attr entity :walk)
       (ent/has-attr entity :input)))

(defn walk
  [[cmd evt] entity]
  (if (can-walk? entity)
    (let [x (ent/attr entity [:position :x])
          op (if (= cmd :left) - +)
          update {:position {:x (op x step)}
                  :walk     {:facing cmd}}]
      (ent/update entity update))
    entity))

; jumping

(defn jump?
  "is jump command?"
  [[cmd evt]]
  (= cmd :jump))

(defn can-jump?
  [entity]
  (and (ent/has-attr entity :position)
       (ent/has-attr entity :jump)
       (ent/has-attr entity :input)))

; gravity applied in (gravity)
(defn elevate
  [entity]
  (let [y (ent/attr entity [:position :y])
        v (ent/attr entity [:jump :velocity])
        update {:position {:y (+ y v)}}]
    (ent/update entity update)))

(defn start-jump
  [entity]
  (let [y (ent/attr entity [:position :y])
        update {:jump {:velocity (* step -1)
                       :on-ground false
                       :start-y y}}
        new-ent (ent/update entity update)]
    (elevate new-ent)))

(defn end-jump
  [entity]
  (let [limit (* (/ step 2) -1)
        velocity (ent/attr entity [:jump :velocity])]
    (if (< velocity limit)
      (ent/attr entity [:jump :velocity] limit)
      entity)))

(defn jump
  [cmd entity]
  (if (can-jump? entity)
    (condp = cmd
      [:jump :start] (if (ent/attr entity [:jump :on-ground])
                       (start-jump entity)
                       (elevate entity))
      [:jump :end] (end-jump entity)
      :else (elevate entity))
    entity))

(defn apply-command
  [cmd world]
  (cond
   (walk? cmd) (map #(walk cmd %) world)
   (jump? cmd) (map #(jump cmd %) world)
   :else (println "(phys/move) unrecognised input:" cmd)))

(defn move
  [cmd world]
  (if-not (nil? cmd)
    (apply-command cmd world)
    world))

;; Gravity

(defn has-weight?
  [entity]
  (and (ent/has-attr entity :position)
       (ent/has-attr entity :gravity)
       (false? (ent/attr entity [:jump :on-ground]))))

(defn gravity
  [world]
  (for [entity world]
    (if (has-weight? entity)
      (let [vel (ent/attr entity [:jump :velocity])
            new-vel (+ vel gravity)]
        (ent/attr entity [:jump :velocity] new-vel))
      entity)))
