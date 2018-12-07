(ns garamond.logging
  (:require [taoensso.timbre :as timbre]
            [clojure.string :as string]))

(defn- log-message [{:keys [msg_ level ?ns-str ?file ?line]}]
  (let [msg     (force msg_)
        lev-str (->> level name string/upper-case (format "%-6s"))
        where   (str "[" (timbre/color-str :cyan (or ?ns-str ?file "?")) ":" (or ?line "?") "] ")
        color   ({:warn :yellow :error :red :fatal :red :debug :blue} level)]
    (if color
      (str (timbre/color-str color lev-str) where msg)
      msg)))

(defn set-up-logging!
  "Initialize timbe logging."
  [{:keys [verbose]}]
  (timbre/merge-config!
   {:min-level (if verbose :debug :info)
    :output-fn log-message
    :timestamp-opts {:pattern :iso8601}}))

