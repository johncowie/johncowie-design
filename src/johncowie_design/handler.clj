(ns johncowie-design.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [hiccup.page :refer [include-js include-css html5]]
            [hiccup.core :refer [html]]
            [garden.core :refer [css]]
            [garden.units :refer [px pc]]
            [garden.stylesheet :refer [at-media]]
            [ring.util.response :refer [response content-type]])
  (:gen-class))

(def width (px 600))

(defn make-palette [header-bg header-text post-bg post-text links]
  {:header-bg header-bg
   :header-text header-text
   :post-bg post-bg
   :post-text post-text
   :links links})

(def palettes {
:a (make-palette :#BC294E :#26103D :#C7C1C2 :#3D2B5C :#912049)
:b (make-palette :#036564 :#E8DDCB :#E8DDCB :#033649 :#031634)
:c (make-palette :#770000 :#ffffff :#ffffff :#000000 :#ff0000)
})

(def palette (:c palettes))

(defn generate-css []
(css
 [:body
    {:font-family ["\"Open Sans\"" "sans-serif"]
     :font-size (px 18)
     :background-color (:post-bg palette)
     :color (:post-text palette)
     :padding (px 0)
     :margin (px 0)}
  ]
 [:a
    {:color (:links palette)
     :text-decoration :none}
  ]
 [:code
    {:display :block
     :background-color :white
     :margin [[(px 10) (px 0)]]
     :padding (px 10)}
  ]
 [:.nav
    {
     :display :block
     :width "100%"
     :background-color (:header-bg palette)
     }
    [:span {:float :left
            :margin (px 5)
            :padding 0}]
    [:ul {:margin 0
          :padding 0
          :float :right}]
    [:li {:display :inline-block
          :margin (px 5)}]
    [:a  {:color (:header-text palette)}
       [:&.selected
         {:color :#ff0000}
        ]
     ]
  ]
 [:.container {:display :block
               :margin [[(px 0) :auto (px 0) :auto]]
               :width width}
   (at-media {:max-width width}
           [:& {:width "100%"}])]

 [:.header
    {:display :block
     :background-color (:header-bg palette)
     :color (:header-text palette)
     :margin (px 0)
     :padding (px 5)
     :text-align :center
     :border [[:solid (px 1) :black]]
     }
    (at-media {:max-width width}
              [:& {:font-size (px 50)}])
  ]
 [:.content
    {:padding (px 10)
     :line-height 1.7}
  ]
 [:.footer
    {:padding (px 10)}
    [:.next {:float :right}]
    [:.prev {:float :left}]
  ]

 [:.clear-fix {:clear :both}]))

(defn base-template []
   (html5
   [:head
     [:title "Test Design"]
     ;(include-css "http://fonts.googleapis.com/css?family=Open+Sans:300italic,300,400italic,400,600italic,600,700italic,700,800italic,800")
     ;(include-css "js/google-code-prettify/prettify.css")
     [:style (generate-css)]
    ]
   [:body
    [:div.nav
       [:div.container
         [:span {:style "color: #fff;"} "JOHN COWIE"]
         [:ul
           [:li [:a.blog-link {:href "http://blog.johncowie.co.uk"} "Blog"]]
           [:li [:a.books-link {:href "http://books.johncowie.co.uk"} "Books"]]
          ]
         [:div.clear-fix]
        ]
     ]
    [:div.container.content
      [:h1 "Test Header"]
      [:p "This is some test content.  Blahdy blahdy blah."]
     ]]))

(defroutes app-routes
  (GET "/" [] (base-template))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [& args]
  (spit (first args) (base-template))
  )
