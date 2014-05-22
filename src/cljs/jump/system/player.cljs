(ns jump.system.player
  (:require [cljs.core.async :refer [put!]]))

(enable-console-print!)

; taken from
; https://github.com/google/closure-library/blob/master/closure/goog/events/keynames.js
(def keyname
  {27 :esc
   32 :space
   37 :left
   38 :up
   39 :right
   65 :a
   68 :d
   83 :s
   87 :w})

(def command
  {:esc   :pause
   :space :jump
   :up    :jump
   :w     :jump
   :left  :left
   :a     :left
   :right :right
   :d     :right})

(defn input->command
  [input]
  (when-let [cmd (-> (.-keyCode input) keyname command)]
    (println "command" cmd)
    cmd))

(defn input>!chan
  [chan input]
  (when-let [cmd (input->command input)]
    (put! chan cmd)))

(defn bind-controls [cmd-chan]
  (println "Bound controls")
  (set! (.-onkeydown js/document)
        #(input>!chan cmd-chan %)))
