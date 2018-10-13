$(document)
		.ready(
				function() {
					(function(e, t, n) {
						function f(e) {
							var n = t(window).scrollTop();
							var r = n + t(window).height();
							var i = e.offset().top;
							var s = i + e.height();
							return s >= n && i <= r && s <= r && i >= n
						}
						function l(e, t) {
							var n = get_element(t);
							var r, s, o;
							e = e + "<div class='bootstro-nav-wrapper'>";
							if (n.attr("data-bootstro-nextButton")) {
								r = n.attr("data-bootstro-nextButton")
							} else if (n.attr("data-bootstro-nextButtonText")) {
								r = '<button class="btn btn-primary btn-mini bootstro-next-btn">'
										+ n
												.attr("data-bootstro-nextButtonText")
										+ "</button>"
							} else {
								if (typeof a.nextButton != "undefined")
									r = a.nextButton;
								else
									r = '<button class="btn btn-primary btn-mini bootstro-next-btn">'
											+ a.nextButtonText + "</button>"
							}
							if (n.attr("data-bootstro-prevButton")) {
								s = n.attr("data-bootstro-prevButton")
							} else if (n.attr("data-bootstro-prevButtonText")) {
								s = '<button class="btn btn-primary btn-mini bootstro-prev-btn">'
										+ n
												.attr("data-bootstro-prevButtonText")
										+ "</button>"
							} else {
								if (typeof a.prevButton != "undefined")
									s = a.prevButton;
								else
									s = '<button class="btn btn-primary btn-mini bootstro-prev-btn">'
											+ a.prevButtonText + "</button>"
							}
							if (n.attr("data-bootstro-finishButton")) {
								o = n.attr("data-bootstro-finishButton")
							} else if (n.attr("data-bootstro-finishButtonText")) {
								o = '<button class="btn btn-primary btn-mini bootstro-finish-btn">'
										+ n
												.attr("data-bootstro-finishButtonText")
										+ "</button>"
							} else {
								if (typeof a.finishButton != "undefined")
									o = a.finishButton;
								else
									o = '<button class="btn btn-primary btn-mini bootstro-finish-btn">'
											+ a.finishButtonText + "</button>"
							}
							if (i != 1) {
								if (t == 0)
									e = e + r;
								else if (t == i - 1)
									e = e + s;
								else
									e = e + r + s
							}
							e = e + "</div>";
							e = e + '<div class="bootstro-finish-btn-wrapper">'
									+ o + "</div>";
							return e
						}
						var r;
						var i;
						var s = [];
						var o = null;
						var u = {
							nextButtonText : "Next 禄",
							prevButtonText : "芦 Prev",
							finishButtonText : '<i class="icon-ok"></i> Ok I got it, get back to the site',
							stopOnBackdropClick : true,
							stopOnEsc : true,
							margin : 100
						};
						var a;
						get_element = function(e) {
							if (r.filter("[data-bootstro-step=" + e + "]")
									.size() > 0)
								return r.filter("[data-bootstro-step=" + e
										+ "]");
							else {
								return r.eq(e)
							}
						};
						get_popup = function(e) {
							var t = {};
							var n = get_element(e);
							var r = "";
							if (i > 1) {
								r = "<span class='label label-success'>"
										+ (e + 1) + "/" + i + "</span>"
							}
							t.title = n.attr("data-bootstro-title") || "";
							if (t.title != "" && r != "")
								t.title = r + " - " + t.title;
							else if (t.title == "")
								t.title = r;
							t.content = n.attr("data-bootstro-content") || "";
							t.content = l(t.content, e);
							t.placement = n.attr("data-bootstro-placement")
									|| "top";
							var s = "";
							if (n.attr("data-bootstro-width")) {
								t.width = n.attr("data-bootstro-width");
								s = s + "width:"
										+ n.attr("data-bootstro-width") + ";"
							}
							if (n.attr("data-bootstro-height")) {
								t.height = n.attr("data-bootstro-height");
								s = s + "height:"
										+ n.attr("data-bootstro-height") + ";"
							}
							t.trigger = "manual";
							t.html = n.attr("data-bootstro-html") || "top";
							t.template = '<div class="popover" style="'
									+ s
									+ '"><div class="arrow"></div><div class="popover-inner"><h3 class="popover-title"></h3><div class="popover-content"><p></p></div></div>'
									+ "</div>";
							return t
						};
						e.destroy_popover = function(e) {
							var e = e || 0;
							if (e != "all") {
								var t = get_element(e);
								t.popover("destroy").removeClass(
										"bootstro-highlight")
							}
						};
						e.stop = function() {
							e.destroy_popover(o);
							e.unbind();
							t("div.bootstro-backdrop").remove();
							if (typeof a.onExit == "function")
								a.onExit.call(this, {
									idx : o
								})
						};
						e.go_to = function(n) {
							e.destroy_popover(o);
							if (i != 0) {
								var r = get_popup(n);
								var s = get_element(n);
								s.popover(r).popover("show");
								var u = t(window).scrollTop();
								var l = Math.min(t(".popover.in").offset().top,
										s.offset().top);
								var c = l - u;
								if (c < a.margin)
									t("html,body").animate({
										scrollTop : l - a.margin
									}, "slow");
								else if (!f(t(".popover.in")) || !f(s))
									t("html,body").animate({
										scrollTop : l - a.margin
									}, "slow");
								s.addClass("bootstro-highlight");
								o = n
							}
						};
						e.next = function() {
							if (o + 1 == i) {
								if (typeof a.onComplete == "function")
									a.onComplete.call(this, {
										idx : o
									})
							} else {
								e.go_to(o + 1);
								if (typeof a.onStep == "function")
									a.onStep.call(this, {
										idx : o,
										direction : "next"
									})
							}
						};
						e.prev = function() {
							if (o == 0) {
							} else {
								e.go_to(o - 1);
								if (typeof a.onStep == "function")
									a.onStep.call(this, {
										idx : o,
										direction : "prev"
									})
							}
						};
						e._start = function(n) {
							n = n || ".bootstro";
							r = t(n);
							i = r.size();
							if (i > 0
									&& t("div.bootstro-backdrop").length === 0) {
								t('<div class="bootstro-backdrop"></div>')
										.appendTo("body");
								e.bind();
								e.go_to(0)
							}
						};
						e.start = function(n, r) {
							a = t.extend(true, {}, u);
							t.extend(a, r || {});
							if (typeof a.url != "undefined") {
								t.ajax({
									url : a.url,
									success : function(r) {
										if (r.success) {
											var i = r.result;
											var s = [];
											t.each(i, function(e, n) {
												t.each(n, function(e, r) {
													t(n.selector).attr(
															"data-bootstro-"
																	+ e, r)
												});
												if (t(n.selector)
														.is(":visible"))
													s.push(n.selector)
											});
											n = s.join(",");
											e._start(n)
										}
									}
								})
							} else {
								e._start(n)
							}
						};
						e.bind = function() {
							e.unbind();
							t("html").on("click.bootstro",
									".bootstro-next-btn", function(t) {
										e.next();
										t.preventDefault();
										return false
									});
							t("html").on("click.bootstro",
									".bootstro-prev-btn", function(t) {
										e.prev();
										t.preventDefault();
										return false
									});
							t("html").on("click.bootstro",
									".bootstro-finish-btn", function(t) {
										e.stop()
									});
							if (a.stopOnBackdropClick) {
								t("html").on(
										"click.bootstro",
										"div.bootstro-backdrop",
										function(n) {
											if (t(n.target).hasClass(
													"bootstro-backdrop"))
												e.stop()
										})
							}
							t(document).on("keydown.bootstro", function(t) {
								var n = t.keyCode ? t.keyCode : t.which;
								if (n == 39 || n == 40)
									e.next();
								else if (n == 37 || n == 38)
									e.prev();
								else if (n == 27 && a.stopOnEsc)
									e.stop()
							})
						};
						e.unbind = function() {
							t("html").unbind("click.bootstro");
							t(document).unbind("keydown.bootstro")
						}
					})(window.bootstro = window.bootstro || {}, jQuery)
				})