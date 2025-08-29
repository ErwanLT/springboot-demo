class Uo {
  constructor() {
    this.eventBuffer = [], this.handledTypes = [], this.copilotMain = null, this.debug = !1, this.eventProxy = {
      functionCallQueue: [],
      dispatchEvent(...t) {
        return this.functionCallQueue.push({ name: "dispatchEvent", args: t }), !0;
      },
      removeEventListener(...t) {
        this.functionCallQueue.push({ name: "removeEventListener", args: t });
      },
      addEventListener(...t) {
        this.functionCallQueue.push({ name: "addEventListener", args: t });
      },
      processQueue(t) {
        this.functionCallQueue.forEach((n) => {
          t[n.name].call(t, ...n.args);
        }), this.functionCallQueue = [];
      }
    };
  }
  getEventTarget() {
    return this.copilotMain ? this.copilotMain : (this.copilotMain = document.querySelector("copilot-main"), this.copilotMain ? (this.eventProxy.processQueue(this.copilotMain), this.copilotMain) : this.eventProxy);
  }
  on(t, n) {
    const r = n;
    return this.getEventTarget().addEventListener(t, r), this.handledTypes.push(t), this.flush(t), () => this.off(t, r);
  }
  once(t, n) {
    this.getEventTarget().addEventListener(t, n, { once: !0 });
  }
  off(t, n) {
    this.getEventTarget().removeEventListener(t, n);
    const r = this.handledTypes.indexOf(t, 0);
    r > -1 && this.handledTypes.splice(r, 1);
  }
  emit(t, n) {
    const r = new CustomEvent(t, { detail: n, cancelable: !0 });
    return this.handledTypes.includes(t) || this.eventBuffer.push(r), this.debug && console.debug("Emit event", r), this.getEventTarget().dispatchEvent(r), r.defaultPrevented;
  }
  emitUnsafe({ type: t, data: n }) {
    return this.emit(t, n);
  }
  // Communication with server via eventbus
  send(t, n) {
    const r = new CustomEvent("copilot-send", { detail: { command: t, data: n } });
    this.getEventTarget().dispatchEvent(r);
  }
  // Listeners for Copilot itself
  onSend(t) {
    this.on("copilot-send", t);
  }
  offSend(t) {
    this.off("copilot-send", t);
  }
  flush(t) {
    const n = [];
    this.eventBuffer.filter((r) => r.type === t).forEach((r) => {
      this.getEventTarget().dispatchEvent(r), n.push(r);
    }), this.eventBuffer = this.eventBuffer.filter((r) => !n.includes(r));
  }
}
var Bo = {
  0: "Invalid value for configuration 'enforceActions', expected 'never', 'always' or 'observed'",
  1: function(t, n) {
    return "Cannot apply '" + t + "' to '" + n.toString() + "': Field not found.";
  },
  /*
  2(prop) {
      return `invalid decorator for '${prop.toString()}'`
  },
  3(prop) {
      return `Cannot decorate '${prop.toString()}': action can only be used on properties with a function value.`
  },
  4(prop) {
      return `Cannot decorate '${prop.toString()}': computed can only be used on getter properties.`
  },
  */
  5: "'keys()' can only be used on observable objects, arrays, sets and maps",
  6: "'values()' can only be used on observable objects, arrays, sets and maps",
  7: "'entries()' can only be used on observable objects, arrays and maps",
  8: "'set()' can only be used on observable objects, arrays and maps",
  9: "'remove()' can only be used on observable objects, arrays and maps",
  10: "'has()' can only be used on observable objects, arrays and maps",
  11: "'get()' can only be used on observable objects, arrays and maps",
  12: "Invalid annotation",
  13: "Dynamic observable objects cannot be frozen. If you're passing observables to 3rd party component/function that calls Object.freeze, pass copy instead: toJS(observable)",
  14: "Intercept handlers should return nothing or a change object",
  15: "Observable arrays cannot be frozen. If you're passing observables to 3rd party component/function that calls Object.freeze, pass copy instead: toJS(observable)",
  16: "Modification exception: the internal structure of an observable array was changed.",
  17: function(t, n) {
    return "[mobx.array] Index out of bounds, " + t + " is larger than " + n;
  },
  18: "mobx.map requires Map polyfill for the current browser. Check babel-polyfill or core-js/es6/map.js",
  19: function(t) {
    return "Cannot initialize from classes that inherit from Map: " + t.constructor.name;
  },
  20: function(t) {
    return "Cannot initialize map from " + t;
  },
  21: function(t) {
    return "Cannot convert to map from '" + t + "'";
  },
  22: "mobx.set requires Set polyfill for the current browser. Check babel-polyfill or core-js/es6/set.js",
  23: "It is not possible to get index atoms from arrays",
  24: function(t) {
    return "Cannot obtain administration from " + t;
  },
  25: function(t, n) {
    return "the entry '" + t + "' does not exist in the observable map '" + n + "'";
  },
  26: "please specify a property",
  27: function(t, n) {
    return "no observable property '" + t.toString() + "' found on the observable object '" + n + "'";
  },
  28: function(t) {
    return "Cannot obtain atom from " + t;
  },
  29: "Expecting some object",
  30: "invalid action stack. did you forget to finish an action?",
  31: "missing option for computed: get",
  32: function(t, n) {
    return "Cycle detected in computation " + t + ": " + n;
  },
  33: function(t) {
    return "The setter of computed value '" + t + "' is trying to update itself. Did you intend to update an _observable_ value, instead of the computed property?";
  },
  34: function(t) {
    return "[ComputedValue '" + t + "'] It is not possible to assign a new value to a computed value.";
  },
  35: "There are multiple, different versions of MobX active. Make sure MobX is loaded only once or use `configure({ isolateGlobalState: true })`",
  36: "isolateGlobalState should be called before MobX is running any reactions",
  37: function(t) {
    return "[mobx] `observableArray." + t + "()` mutates the array in-place, which is not allowed inside a derivation. Use `array.slice()." + t + "()` instead";
  },
  38: "'ownKeys()' can only be used on observable objects",
  39: "'defineProperty()' can only be used on observable objects"
}, Fo = process.env.NODE_ENV !== "production" ? Bo : {};
function p(e) {
  for (var t = arguments.length, n = new Array(t > 1 ? t - 1 : 0), r = 1; r < t; r++)
    n[r - 1] = arguments[r];
  if (process.env.NODE_ENV !== "production") {
    var i = typeof e == "string" ? e : Fo[e];
    throw typeof i == "function" && (i = i.apply(null, n)), new Error("[MobX] " + i);
  }
  throw new Error(typeof e == "number" ? "[MobX] minified error nr: " + e + (n.length ? " " + n.map(String).join(",") : "") + ". Find the full error at: https://github.com/mobxjs/mobx/blob/main/packages/mobx/src/errors.ts" : "[MobX] " + e);
}
var Ho = {};
function Kn() {
  return typeof globalThis < "u" ? globalThis : typeof window < "u" ? window : typeof global < "u" ? global : typeof self < "u" ? self : Ho;
}
var li = Object.assign, Bt = Object.getOwnPropertyDescriptor, Z = Object.defineProperty, nn = Object.prototype, Ft = [];
Object.freeze(Ft);
var Wn = {};
Object.freeze(Wn);
var qo = typeof Proxy < "u", Ko = /* @__PURE__ */ Object.toString();
function ci() {
  qo || p(process.env.NODE_ENV !== "production" ? "`Proxy` objects are not available in the current environment. Please configure MobX to enable a fallback implementation.`" : "Proxy not available");
}
function it(e) {
  process.env.NODE_ENV !== "production" && f.verifyProxies && p("MobX is currently configured to be able to run in ES5 mode, but in ES5 MobX won't be able to " + e);
}
function F() {
  return ++f.mobxGuid;
}
function Gn(e) {
  var t = !1;
  return function() {
    if (!t)
      return t = !0, e.apply(this, arguments);
  };
}
var ze = function() {
};
function A(e) {
  return typeof e == "function";
}
function Se(e) {
  var t = typeof e;
  switch (t) {
    case "string":
    case "symbol":
    case "number":
      return !0;
  }
  return !1;
}
function rn(e) {
  return e !== null && typeof e == "object";
}
function P(e) {
  if (!rn(e))
    return !1;
  var t = Object.getPrototypeOf(e);
  if (t == null)
    return !0;
  var n = Object.hasOwnProperty.call(t, "constructor") && t.constructor;
  return typeof n == "function" && n.toString() === Ko;
}
function ui(e) {
  var t = e?.constructor;
  return t ? t.name === "GeneratorFunction" || t.displayName === "GeneratorFunction" : !1;
}
function on(e, t, n) {
  Z(e, t, {
    enumerable: !1,
    writable: !0,
    configurable: !0,
    value: n
  });
}
function di(e, t, n) {
  Z(e, t, {
    enumerable: !1,
    writable: !1,
    configurable: !0,
    value: n
  });
}
function ke(e, t) {
  var n = "isMobX" + e;
  return t.prototype[n] = !0, function(r) {
    return rn(r) && r[n] === !0;
  };
}
function Je(e) {
  return e != null && Object.prototype.toString.call(e) === "[object Map]";
}
function Wo(e) {
  var t = Object.getPrototypeOf(e), n = Object.getPrototypeOf(t), r = Object.getPrototypeOf(n);
  return r === null;
}
function re(e) {
  return e != null && Object.prototype.toString.call(e) === "[object Set]";
}
var fi = typeof Object.getOwnPropertySymbols < "u";
function Go(e) {
  var t = Object.keys(e);
  if (!fi)
    return t;
  var n = Object.getOwnPropertySymbols(e);
  return n.length ? [].concat(t, n.filter(function(r) {
    return nn.propertyIsEnumerable.call(e, r);
  })) : t;
}
var mt = typeof Reflect < "u" && Reflect.ownKeys ? Reflect.ownKeys : fi ? function(e) {
  return Object.getOwnPropertyNames(e).concat(Object.getOwnPropertySymbols(e));
} : (
  /* istanbul ignore next */
  Object.getOwnPropertyNames
);
function $n(e) {
  return typeof e == "string" ? e : typeof e == "symbol" ? e.toString() : new String(e).toString();
}
function pi(e) {
  return e === null ? null : typeof e == "object" ? "" + e : e;
}
function q(e, t) {
  return nn.hasOwnProperty.call(e, t);
}
var Yo = Object.getOwnPropertyDescriptors || function(t) {
  var n = {};
  return mt(t).forEach(function(r) {
    n[r] = Bt(t, r);
  }), n;
};
function D(e, t) {
  return !!(e & t);
}
function k(e, t, n) {
  return n ? e |= t : e &= ~t, e;
}
function cr(e, t) {
  (t == null || t > e.length) && (t = e.length);
  for (var n = 0, r = Array(t); n < t; n++) r[n] = e[n];
  return r;
}
function Jo(e, t) {
  for (var n = 0; n < t.length; n++) {
    var r = t[n];
    r.enumerable = r.enumerable || !1, r.configurable = !0, "value" in r && (r.writable = !0), Object.defineProperty(e, Zo(r.key), r);
  }
}
function Xe(e, t, n) {
  return t && Jo(e.prototype, t), Object.defineProperty(e, "prototype", {
    writable: !1
  }), e;
}
function Ue(e, t) {
  var n = typeof Symbol < "u" && e[Symbol.iterator] || e["@@iterator"];
  if (n) return (n = n.call(e)).next.bind(n);
  if (Array.isArray(e) || (n = Qo(e)) || t) {
    n && (e = n);
    var r = 0;
    return function() {
      return r >= e.length ? {
        done: !0
      } : {
        done: !1,
        value: e[r++]
      };
    };
  }
  throw new TypeError(`Invalid attempt to iterate non-iterable instance.
In order to be iterable, non-array objects must have a [Symbol.iterator]() method.`);
}
function ve() {
  return ve = Object.assign ? Object.assign.bind() : function(e) {
    for (var t = 1; t < arguments.length; t++) {
      var n = arguments[t];
      for (var r in n) ({}).hasOwnProperty.call(n, r) && (e[r] = n[r]);
    }
    return e;
  }, ve.apply(null, arguments);
}
function vi(e, t) {
  e.prototype = Object.create(t.prototype), e.prototype.constructor = e, Dn(e, t);
}
function Dn(e, t) {
  return Dn = Object.setPrototypeOf ? Object.setPrototypeOf.bind() : function(n, r) {
    return n.__proto__ = r, n;
  }, Dn(e, t);
}
function Xo(e, t) {
  if (typeof e != "object" || !e) return e;
  var n = e[Symbol.toPrimitive];
  if (n !== void 0) {
    var r = n.call(e, t);
    if (typeof r != "object") return r;
    throw new TypeError("@@toPrimitive must return a primitive value.");
  }
  return String(e);
}
function Zo(e) {
  var t = Xo(e, "string");
  return typeof t == "symbol" ? t : t + "";
}
function Qo(e, t) {
  if (e) {
    if (typeof e == "string") return cr(e, t);
    var n = {}.toString.call(e).slice(8, -1);
    return n === "Object" && e.constructor && (n = e.constructor.name), n === "Map" || n === "Set" ? Array.from(e) : n === "Arguments" || /^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(n) ? cr(e, t) : void 0;
  }
}
var ie = /* @__PURE__ */ Symbol("mobx-stored-annotations");
function Q(e) {
  function t(n, r) {
    if (St(r))
      return e.decorate_20223_(n, r);
    xt(n, r, e);
  }
  return Object.assign(t, e);
}
function xt(e, t, n) {
  if (q(e, ie) || on(e, ie, ve({}, e[ie])), process.env.NODE_ENV !== "production" && Ht(n) && !q(e[ie], t)) {
    var r = e.constructor.name + ".prototype." + t.toString();
    p("'" + r + "' is decorated with 'override', but no such decorated member was found on prototype.");
  }
  ea(e, n, t), Ht(n) || (e[ie][t] = n);
}
function ea(e, t, n) {
  if (process.env.NODE_ENV !== "production" && !Ht(t) && q(e[ie], n)) {
    var r = e.constructor.name + ".prototype." + n.toString(), i = e[ie][n].annotationType_, o = t.annotationType_;
    p("Cannot apply '@" + o + "' to '" + r + "':" + (`
The field is already decorated with '@` + i + "'.") + `
Re-decorating fields is not allowed.
Use '@override' decorator for methods overridden by subclass.`);
  }
}
function St(e) {
  return typeof e == "object" && typeof e.kind == "string";
}
function an(e, t) {
  process.env.NODE_ENV !== "production" && !t.includes(e.kind) && p("The decorator applied to '" + String(e.name) + "' cannot be used on a " + e.kind + " element");
}
var m = /* @__PURE__ */ Symbol("mobx administration"), ge = /* @__PURE__ */ function() {
  function e(n) {
    n === void 0 && (n = process.env.NODE_ENV !== "production" ? "Atom@" + F() : "Atom"), this.name_ = void 0, this.flags_ = 0, this.observers_ = /* @__PURE__ */ new Set(), this.lastAccessedBy_ = 0, this.lowestObserverState_ = _.NOT_TRACKING_, this.onBOL = void 0, this.onBUOL = void 0, this.name_ = n;
  }
  var t = e.prototype;
  return t.onBO = function() {
    this.onBOL && this.onBOL.forEach(function(r) {
      return r();
    });
  }, t.onBUO = function() {
    this.onBUOL && this.onBUOL.forEach(function(r) {
      return r();
    });
  }, t.reportObserved = function() {
    return $i(this);
  }, t.reportChanged = function() {
    L(), Di(this), z();
  }, t.toString = function() {
    return this.name_;
  }, Xe(e, [{
    key: "isBeingObserved",
    get: function() {
      return D(this.flags_, e.isBeingObservedMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isBeingObservedMask_, r);
    }
  }, {
    key: "isPendingUnobservation",
    get: function() {
      return D(this.flags_, e.isPendingUnobservationMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isPendingUnobservationMask_, r);
    }
  }, {
    key: "diffValue",
    get: function() {
      return D(this.flags_, e.diffValueMask_) ? 1 : 0;
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.diffValueMask_, r === 1);
    }
  }]);
}();
ge.isBeingObservedMask_ = 1;
ge.isPendingUnobservationMask_ = 2;
ge.diffValueMask_ = 4;
var Yn = /* @__PURE__ */ ke("Atom", ge);
function hi(e, t, n) {
  t === void 0 && (t = ze), n === void 0 && (n = ze);
  var r = new ge(e);
  return t !== ze && us(r, t), n !== ze && zi(r, n), r;
}
function ta(e, t) {
  return Zi(e, t);
}
function na(e, t) {
  return Object.is ? Object.is(e, t) : e === t ? e !== 0 || 1 / e === 1 / t : e !== e && t !== t;
}
var He = {
  structural: ta,
  default: na
};
function Ne(e, t, n) {
  return yt(e) ? e : Array.isArray(e) ? x.array(e, {
    name: n
  }) : P(e) ? x.object(e, void 0, {
    name: n
  }) : Je(e) ? x.map(e, {
    name: n
  }) : re(e) ? x.set(e, {
    name: n
  }) : typeof e == "function" && !qe(e) && !_t(e) ? ui(e) ? Ke(e) : bt(n, e) : e;
}
function ra(e, t, n) {
  if (e == null || et(e) || pn(e) || me(e) || J(e))
    return e;
  if (Array.isArray(e))
    return x.array(e, {
      name: n,
      deep: !1
    });
  if (P(e))
    return x.object(e, void 0, {
      name: n,
      deep: !1
    });
  if (Je(e))
    return x.map(e, {
      name: n,
      deep: !1
    });
  if (re(e))
    return x.set(e, {
      name: n,
      deep: !1
    });
  process.env.NODE_ENV !== "production" && p("The shallow modifier / decorator can only used in combination with arrays, objects, maps and sets");
}
function sn(e) {
  return e;
}
function ia(e, t) {
  return process.env.NODE_ENV !== "production" && yt(e) && p("observable.struct should not be used with observable values"), Zi(e, t) ? t : e;
}
var oa = "override";
function Ht(e) {
  return e.annotationType_ === oa;
}
function Nt(e, t) {
  return {
    annotationType_: e,
    options_: t,
    make_: aa,
    extend_: sa,
    decorate_20223_: la
  };
}
function aa(e, t, n, r) {
  var i;
  if ((i = this.options_) != null && i.bound)
    return this.extend_(e, t, n, !1) === null ? 0 : 1;
  if (r === e.target_)
    return this.extend_(e, t, n, !1) === null ? 0 : 2;
  if (qe(n.value))
    return 1;
  var o = gi(e, this, t, n, !1);
  return Z(r, t, o), 2;
}
function sa(e, t, n, r) {
  var i = gi(e, this, t, n);
  return e.defineProperty_(t, i, r);
}
function la(e, t) {
  process.env.NODE_ENV !== "production" && an(t, ["method", "field"]);
  var n = t.kind, r = t.name, i = t.addInitializer, o = this, a = function(c) {
    var u, d, v, h;
    return Ce((u = (d = o.options_) == null ? void 0 : d.name) != null ? u : r.toString(), c, (v = (h = o.options_) == null ? void 0 : h.autoAction) != null ? v : !1);
  };
  if (n == "field")
    return function(l) {
      var c, u = l;
      return qe(u) || (u = a(u)), (c = o.options_) != null && c.bound && (u = u.bind(this), u.isMobxAction = !0), u;
    };
  if (n == "method") {
    var s;
    return qe(e) || (e = a(e)), (s = this.options_) != null && s.bound && i(function() {
      var l = this, c = l[r].bind(l);
      c.isMobxAction = !0, l[r] = c;
    }), e;
  }
  p("Cannot apply '" + o.annotationType_ + "' to '" + String(r) + "' (kind: " + n + "):" + (`
'` + o.annotationType_ + "' can only be used on properties with a function value."));
}
function ca(e, t, n, r) {
  var i = t.annotationType_, o = r.value;
  process.env.NODE_ENV !== "production" && !A(o) && p("Cannot apply '" + i + "' to '" + e.name_ + "." + n.toString() + "':" + (`
'` + i + "' can only be used on properties with a function value."));
}
function gi(e, t, n, r, i) {
  var o, a, s, l, c, u, d;
  i === void 0 && (i = f.safeDescriptors), ca(e, t, n, r);
  var v = r.value;
  if ((o = t.options_) != null && o.bound) {
    var h;
    v = v.bind((h = e.proxy_) != null ? h : e.target_);
  }
  return {
    value: Ce(
      (a = (s = t.options_) == null ? void 0 : s.name) != null ? a : n.toString(),
      v,
      (l = (c = t.options_) == null ? void 0 : c.autoAction) != null ? l : !1,
      // https://github.com/mobxjs/mobx/discussions/3140
      (u = t.options_) != null && u.bound ? (d = e.proxy_) != null ? d : e.target_ : void 0
    ),
    // Non-configurable for classes
    // prevents accidental field redefinition in subclass
    configurable: i ? e.isPlainObject_ : !0,
    // https://github.com/mobxjs/mobx/pull/2641#issuecomment-737292058
    enumerable: !1,
    // Non-obsevable, therefore non-writable
    // Also prevents rewriting in subclass constructor
    writable: !i
  };
}
function mi(e, t) {
  return {
    annotationType_: e,
    options_: t,
    make_: ua,
    extend_: da,
    decorate_20223_: fa
  };
}
function ua(e, t, n, r) {
  var i;
  if (r === e.target_)
    return this.extend_(e, t, n, !1) === null ? 0 : 2;
  if ((i = this.options_) != null && i.bound && (!q(e.target_, t) || !_t(e.target_[t])) && this.extend_(e, t, n, !1) === null)
    return 0;
  if (_t(n.value))
    return 1;
  var o = bi(e, this, t, n, !1, !1);
  return Z(r, t, o), 2;
}
function da(e, t, n, r) {
  var i, o = bi(e, this, t, n, (i = this.options_) == null ? void 0 : i.bound);
  return e.defineProperty_(t, o, r);
}
function fa(e, t) {
  var n;
  process.env.NODE_ENV !== "production" && an(t, ["method"]);
  var r = t.name, i = t.addInitializer;
  return _t(e) || (e = Ke(e)), (n = this.options_) != null && n.bound && i(function() {
    var o = this, a = o[r].bind(o);
    a.isMobXFlow = !0, o[r] = a;
  }), e;
}
function pa(e, t, n, r) {
  var i = t.annotationType_, o = r.value;
  process.env.NODE_ENV !== "production" && !A(o) && p("Cannot apply '" + i + "' to '" + e.name_ + "." + n.toString() + "':" + (`
'` + i + "' can only be used on properties with a generator function value."));
}
function bi(e, t, n, r, i, o) {
  o === void 0 && (o = f.safeDescriptors), pa(e, t, n, r);
  var a = r.value;
  if (_t(a) || (a = Ke(a)), i) {
    var s;
    a = a.bind((s = e.proxy_) != null ? s : e.target_), a.isMobXFlow = !0;
  }
  return {
    value: a,
    // Non-configurable for classes
    // prevents accidental field redefinition in subclass
    configurable: o ? e.isPlainObject_ : !0,
    // https://github.com/mobxjs/mobx/pull/2641#issuecomment-737292058
    enumerable: !1,
    // Non-obsevable, therefore non-writable
    // Also prevents rewriting in subclass constructor
    writable: !o
  };
}
function Jn(e, t) {
  return {
    annotationType_: e,
    options_: t,
    make_: va,
    extend_: ha,
    decorate_20223_: ga
  };
}
function va(e, t, n) {
  return this.extend_(e, t, n, !1) === null ? 0 : 1;
}
function ha(e, t, n, r) {
  return ma(e, this, t, n), e.defineComputedProperty_(t, ve({}, this.options_, {
    get: n.get,
    set: n.set
  }), r);
}
function ga(e, t) {
  process.env.NODE_ENV !== "production" && an(t, ["getter"]);
  var n = this, r = t.name, i = t.addInitializer;
  return i(function() {
    var o = Qe(this)[m], a = ve({}, n.options_, {
      get: e,
      context: this
    });
    a.name || (a.name = process.env.NODE_ENV !== "production" ? o.name_ + "." + r.toString() : "ObservableObject." + r.toString()), o.values_.set(r, new B(a));
  }), function() {
    return this[m].getObservablePropValue_(r);
  };
}
function ma(e, t, n, r) {
  var i = t.annotationType_, o = r.get;
  process.env.NODE_ENV !== "production" && !o && p("Cannot apply '" + i + "' to '" + e.name_ + "." + n.toString() + "':" + (`
'` + i + "' can only be used on getter(+setter) properties."));
}
function ln(e, t) {
  return {
    annotationType_: e,
    options_: t,
    make_: ba,
    extend_: _a,
    decorate_20223_: ya
  };
}
function ba(e, t, n) {
  return this.extend_(e, t, n, !1) === null ? 0 : 1;
}
function _a(e, t, n, r) {
  var i, o;
  return wa(e, this, t, n), e.defineObservableProperty_(t, n.value, (i = (o = this.options_) == null ? void 0 : o.enhancer) != null ? i : Ne, r);
}
function ya(e, t) {
  if (process.env.NODE_ENV !== "production") {
    if (t.kind === "field")
      throw p("Please use `@observable accessor " + String(t.name) + "` instead of `@observable " + String(t.name) + "`");
    an(t, ["accessor"]);
  }
  var n = this, r = t.kind, i = t.name, o = /* @__PURE__ */ new WeakSet();
  function a(s, l) {
    var c, u, d = Qe(s)[m], v = new xe(l, (c = (u = n.options_) == null ? void 0 : u.enhancer) != null ? c : Ne, process.env.NODE_ENV !== "production" ? d.name_ + "." + i.toString() : "ObservableObject." + i.toString(), !1);
    d.values_.set(i, v), o.add(s);
  }
  if (r == "accessor")
    return {
      get: function() {
        return o.has(this) || a(this, e.get.call(this)), this[m].getObservablePropValue_(i);
      },
      set: function(l) {
        return o.has(this) || a(this, l), this[m].setObservablePropValue_(i, l);
      },
      init: function(l) {
        return o.has(this) || a(this, l), l;
      }
    };
}
function wa(e, t, n, r) {
  var i = t.annotationType_;
  process.env.NODE_ENV !== "production" && !("value" in r) && p("Cannot apply '" + i + "' to '" + e.name_ + "." + n.toString() + "':" + (`
'` + i + "' cannot be used on getter/setter properties"));
}
var Ea = "true", Oa = /* @__PURE__ */ _i();
function _i(e) {
  return {
    annotationType_: Ea,
    options_: e,
    make_: Aa,
    extend_: xa,
    decorate_20223_: Sa
  };
}
function Aa(e, t, n, r) {
  var i, o;
  if (n.get)
    return cn.make_(e, t, n, r);
  if (n.set) {
    var a = Ce(t.toString(), n.set);
    return r === e.target_ ? e.defineProperty_(t, {
      configurable: f.safeDescriptors ? e.isPlainObject_ : !0,
      set: a
    }) === null ? 0 : 2 : (Z(r, t, {
      configurable: !0,
      set: a
    }), 2);
  }
  if (r !== e.target_ && typeof n.value == "function") {
    var s;
    if (ui(n.value)) {
      var l, c = (l = this.options_) != null && l.autoBind ? Ke.bound : Ke;
      return c.make_(e, t, n, r);
    }
    var u = (s = this.options_) != null && s.autoBind ? bt.bound : bt;
    return u.make_(e, t, n, r);
  }
  var d = ((i = this.options_) == null ? void 0 : i.deep) === !1 ? x.ref : x;
  if (typeof n.value == "function" && (o = this.options_) != null && o.autoBind) {
    var v;
    n.value = n.value.bind((v = e.proxy_) != null ? v : e.target_);
  }
  return d.make_(e, t, n, r);
}
function xa(e, t, n, r) {
  var i, o;
  if (n.get)
    return cn.extend_(e, t, n, r);
  if (n.set)
    return e.defineProperty_(t, {
      configurable: f.safeDescriptors ? e.isPlainObject_ : !0,
      set: Ce(t.toString(), n.set)
    }, r);
  if (typeof n.value == "function" && (i = this.options_) != null && i.autoBind) {
    var a;
    n.value = n.value.bind((a = e.proxy_) != null ? a : e.target_);
  }
  var s = ((o = this.options_) == null ? void 0 : o.deep) === !1 ? x.ref : x;
  return s.extend_(e, t, n, r);
}
function Sa(e, t) {
  p("'" + this.annotationType_ + "' cannot be used as a decorator");
}
var Na = "observable", Ca = "observable.ref", Pa = "observable.shallow", $a = "observable.struct", yi = {
  deep: !0,
  name: void 0,
  defaultDecorator: void 0,
  proxy: !0
};
Object.freeze(yi);
function $t(e) {
  return e || yi;
}
var kn = /* @__PURE__ */ ln(Na), Da = /* @__PURE__ */ ln(Ca, {
  enhancer: sn
}), ka = /* @__PURE__ */ ln(Pa, {
  enhancer: ra
}), Ta = /* @__PURE__ */ ln($a, {
  enhancer: ia
}), wi = /* @__PURE__ */ Q(kn);
function Dt(e) {
  return e.deep === !0 ? Ne : e.deep === !1 ? sn : Va(e.defaultDecorator);
}
function Ia(e) {
  var t;
  return e ? (t = e.defaultDecorator) != null ? t : _i(e) : void 0;
}
function Va(e) {
  var t, n;
  return e && (t = (n = e.options_) == null ? void 0 : n.enhancer) != null ? t : Ne;
}
function Ei(e, t, n) {
  if (St(t))
    return kn.decorate_20223_(e, t);
  if (Se(t)) {
    xt(e, t, kn);
    return;
  }
  return yt(e) ? e : P(e) ? x.object(e, t, n) : Array.isArray(e) ? x.array(e, t) : Je(e) ? x.map(e, t) : re(e) ? x.set(e, t) : typeof e == "object" && e !== null ? e : x.box(e, t);
}
li(Ei, wi);
var Ra = {
  box: function(t, n) {
    var r = $t(n);
    return new xe(t, Dt(r), r.name, !0, r.equals);
  },
  array: function(t, n) {
    var r = $t(n);
    return (f.useProxies === !1 || r.proxy === !1 ? ks : Es)(t, Dt(r), r.name);
  },
  map: function(t, n) {
    var r = $t(n);
    return new Ki(t, Dt(r), r.name);
  },
  set: function(t, n) {
    var r = $t(n);
    return new Wi(t, Dt(r), r.name);
  },
  object: function(t, n, r) {
    return Ie(function() {
      return Bi(f.useProxies === !1 || r?.proxy === !1 ? Qe({}, r) : _s({}, r), t, n);
    });
  },
  ref: /* @__PURE__ */ Q(Da),
  shallow: /* @__PURE__ */ Q(ka),
  deep: wi,
  struct: /* @__PURE__ */ Q(Ta)
}, x = /* @__PURE__ */ li(Ei, Ra), Oi = "computed", ja = "computed.struct", Tn = /* @__PURE__ */ Jn(Oi), Ma = /* @__PURE__ */ Jn(ja, {
  equals: He.structural
}), cn = function(t, n) {
  if (St(n))
    return Tn.decorate_20223_(t, n);
  if (Se(n))
    return xt(t, n, Tn);
  if (P(t))
    return Q(Jn(Oi, t));
  process.env.NODE_ENV !== "production" && (A(t) || p("First argument to `computed` should be an expression."), A(n) && p("A setter as second argument is no longer supported, use `{ set: fn }` option instead"));
  var r = P(n) ? n : {};
  return r.get = t, r.name || (r.name = t.name || ""), new B(r);
};
Object.assign(cn, Tn);
cn.struct = /* @__PURE__ */ Q(Ma);
var ur, dr, qt = 0, La = 1, za = (ur = (dr = /* @__PURE__ */ Bt(function() {
}, "name")) == null ? void 0 : dr.configurable) != null ? ur : !1, fr = {
  value: "action",
  configurable: !0,
  writable: !1,
  enumerable: !1
};
function Ce(e, t, n, r) {
  n === void 0 && (n = !1), process.env.NODE_ENV !== "production" && (A(t) || p("`action` can only be invoked on functions"), (typeof e != "string" || !e) && p("actions should have valid names, got: '" + e + "'"));
  function i() {
    return Ai(e, n, t, r || this, arguments);
  }
  return i.isMobxAction = !0, i.toString = function() {
    return t.toString();
  }, za && (fr.value = e, Z(i, "name", fr)), i;
}
function Ai(e, t, n, r, i) {
  var o = Ua(e, t, r, i);
  try {
    return n.apply(r, i);
  } catch (a) {
    throw o.error_ = a, a;
  } finally {
    Ba(o);
  }
}
function Ua(e, t, n, r) {
  var i = process.env.NODE_ENV !== "production" && C() && !!e, o = 0;
  if (process.env.NODE_ENV !== "production" && i) {
    o = Date.now();
    var a = r ? Array.from(r) : Ft;
    T({
      type: Zn,
      name: e,
      object: n,
      arguments: a
    });
  }
  var s = f.trackingDerivation, l = !t || !s;
  L();
  var c = f.allowStateChanges;
  l && (Te(), c = un(!0));
  var u = Xn(!0), d = {
    runAsAction_: l,
    prevDerivation_: s,
    prevAllowStateChanges_: c,
    prevAllowStateReads_: u,
    notifySpy_: i,
    startTime_: o,
    actionId_: La++,
    parentActionId_: qt
  };
  return qt = d.actionId_, d;
}
function Ba(e) {
  qt !== e.actionId_ && p(30), qt = e.parentActionId_, e.error_ !== void 0 && (f.suppressReactionErrors = !0), dn(e.prevAllowStateChanges_), vt(e.prevAllowStateReads_), z(), e.runAsAction_ && ae(e.prevDerivation_), process.env.NODE_ENV !== "production" && e.notifySpy_ && I({
    time: Date.now() - e.startTime_
  }), f.suppressReactionErrors = !1;
}
function Fa(e, t) {
  var n = un(e);
  try {
    return t();
  } finally {
    dn(n);
  }
}
function un(e) {
  var t = f.allowStateChanges;
  return f.allowStateChanges = e, t;
}
function dn(e) {
  f.allowStateChanges = e;
}
var Ha = "create", xe = /* @__PURE__ */ function(e) {
  function t(r, i, o, a, s) {
    var l;
    return o === void 0 && (o = process.env.NODE_ENV !== "production" ? "ObservableValue@" + F() : "ObservableValue"), a === void 0 && (a = !0), s === void 0 && (s = He.default), l = e.call(this, o) || this, l.enhancer = void 0, l.name_ = void 0, l.equals = void 0, l.hasUnreportedChange_ = !1, l.interceptors_ = void 0, l.changeListeners_ = void 0, l.value_ = void 0, l.dehancer = void 0, l.enhancer = i, l.name_ = o, l.equals = s, l.value_ = i(r, void 0, o), process.env.NODE_ENV !== "production" && a && C() && Pe({
      type: Ha,
      object: l,
      observableKind: "value",
      debugObjectName: l.name_,
      newValue: "" + l.value_
    }), l;
  }
  vi(t, e);
  var n = t.prototype;
  return n.dehanceValue = function(i) {
    return this.dehancer !== void 0 ? this.dehancer(i) : i;
  }, n.set = function(i) {
    var o = this.value_;
    if (i = this.prepareNewValue_(i), i !== f.UNCHANGED) {
      var a = C();
      process.env.NODE_ENV !== "production" && a && T({
        type: H,
        object: this,
        observableKind: "value",
        debugObjectName: this.name_,
        newValue: i,
        oldValue: o
      }), this.setNewValue_(i), process.env.NODE_ENV !== "production" && a && I();
    }
  }, n.prepareNewValue_ = function(i) {
    if (X(this), j(this)) {
      var o = M(this, {
        object: this,
        type: H,
        newValue: i
      });
      if (!o)
        return f.UNCHANGED;
      i = o.newValue;
    }
    return i = this.enhancer(i, this.value_, this.name_), this.equals(this.value_, i) ? f.UNCHANGED : i;
  }, n.setNewValue_ = function(i) {
    var o = this.value_;
    this.value_ = i, this.reportChanged(), K(this) && W(this, {
      type: H,
      object: this,
      newValue: i,
      oldValue: o
    });
  }, n.get = function() {
    return this.reportObserved(), this.dehanceValue(this.value_);
  }, n.intercept_ = function(i) {
    return Ct(this, i);
  }, n.observe_ = function(i, o) {
    return o && i({
      observableKind: "value",
      debugObjectName: this.name_,
      object: this,
      type: H,
      newValue: this.value_,
      oldValue: void 0
    }), Pt(this, i);
  }, n.raw = function() {
    return this.value_;
  }, n.toJSON = function() {
    return this.get();
  }, n.toString = function() {
    return this.name_ + "[" + this.value_ + "]";
  }, n.valueOf = function() {
    return pi(this.get());
  }, n[Symbol.toPrimitive] = function() {
    return this.valueOf();
  }, t;
}(ge), B = /* @__PURE__ */ function() {
  function e(n) {
    this.dependenciesState_ = _.NOT_TRACKING_, this.observing_ = [], this.newObserving_ = null, this.observers_ = /* @__PURE__ */ new Set(), this.runId_ = 0, this.lastAccessedBy_ = 0, this.lowestObserverState_ = _.UP_TO_DATE_, this.unboundDepsCount_ = 0, this.value_ = new Kt(null), this.name_ = void 0, this.triggeredBy_ = void 0, this.flags_ = 0, this.derivation = void 0, this.setter_ = void 0, this.isTracing_ = U.NONE, this.scope_ = void 0, this.equals_ = void 0, this.requiresReaction_ = void 0, this.keepAlive_ = void 0, this.onBOL = void 0, this.onBUOL = void 0, n.get || p(31), this.derivation = n.get, this.name_ = n.name || (process.env.NODE_ENV !== "production" ? "ComputedValue@" + F() : "ComputedValue"), n.set && (this.setter_ = Ce(process.env.NODE_ENV !== "production" ? this.name_ + "-setter" : "ComputedValue-setter", n.set)), this.equals_ = n.equals || (n.compareStructural || n.struct ? He.structural : He.default), this.scope_ = n.context, this.requiresReaction_ = n.requiresReaction, this.keepAlive_ = !!n.keepAlive;
  }
  var t = e.prototype;
  return t.onBecomeStale_ = function() {
    Ja(this);
  }, t.onBO = function() {
    this.onBOL && this.onBOL.forEach(function(r) {
      return r();
    });
  }, t.onBUO = function() {
    this.onBUOL && this.onBUOL.forEach(function(r) {
      return r();
    });
  }, t.get = function() {
    if (this.isComputing && p(32, this.name_, this.derivation), f.inBatch === 0 && // !globalState.trackingDerivatpion &&
    this.observers_.size === 0 && !this.keepAlive_)
      In(this) && (this.warnAboutUntrackedRead_(), L(), this.value_ = this.computeValue_(!1), z());
    else if ($i(this), In(this)) {
      var r = f.trackingContext;
      this.keepAlive_ && !r && (f.trackingContext = this), this.trackAndCompute() && Ya(this), f.trackingContext = r;
    }
    var i = this.value_;
    if (Mt(i))
      throw i.cause;
    return i;
  }, t.set = function(r) {
    if (this.setter_) {
      this.isRunningSetter && p(33, this.name_), this.isRunningSetter = !0;
      try {
        this.setter_.call(this.scope_, r);
      } finally {
        this.isRunningSetter = !1;
      }
    } else
      p(34, this.name_);
  }, t.trackAndCompute = function() {
    var r = this.value_, i = (
      /* see #1208 */
      this.dependenciesState_ === _.NOT_TRACKING_
    ), o = this.computeValue_(!0), a = i || Mt(r) || Mt(o) || !this.equals_(r, o);
    return a && (this.value_ = o, process.env.NODE_ENV !== "production" && C() && Pe({
      observableKind: "computed",
      debugObjectName: this.name_,
      object: this.scope_,
      type: "update",
      oldValue: r,
      newValue: o
    })), a;
  }, t.computeValue_ = function(r) {
    this.isComputing = !0;
    var i = un(!1), o;
    if (r)
      o = xi(this, this.derivation, this.scope_);
    else if (f.disableErrorBoundaries === !0)
      o = this.derivation.call(this.scope_);
    else
      try {
        o = this.derivation.call(this.scope_);
      } catch (a) {
        o = new Kt(a);
      }
    return dn(i), this.isComputing = !1, o;
  }, t.suspend_ = function() {
    this.keepAlive_ || (Vn(this), this.value_ = void 0, process.env.NODE_ENV !== "production" && this.isTracing_ !== U.NONE && console.log("[mobx.trace] Computed value '" + this.name_ + "' was suspended and it will recompute on the next access."));
  }, t.observe_ = function(r, i) {
    var o = this, a = !0, s = void 0;
    return Mi(function() {
      var l = o.get();
      if (!a || i) {
        var c = Te();
        r({
          observableKind: "computed",
          debugObjectName: o.name_,
          type: H,
          object: o,
          newValue: l,
          oldValue: s
        }), ae(c);
      }
      a = !1, s = l;
    });
  }, t.warnAboutUntrackedRead_ = function() {
    process.env.NODE_ENV !== "production" && (this.isTracing_ !== U.NONE && console.log("[mobx.trace] Computed value '" + this.name_ + "' is being read outside a reactive context. Doing a full recompute."), (typeof this.requiresReaction_ == "boolean" ? this.requiresReaction_ : f.computedRequiresReaction) && console.warn("[mobx] Computed value '" + this.name_ + "' is being read outside a reactive context. Doing a full recompute."));
  }, t.toString = function() {
    return this.name_ + "[" + this.derivation.toString() + "]";
  }, t.valueOf = function() {
    return pi(this.get());
  }, t[Symbol.toPrimitive] = function() {
    return this.valueOf();
  }, Xe(e, [{
    key: "isComputing",
    get: function() {
      return D(this.flags_, e.isComputingMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isComputingMask_, r);
    }
  }, {
    key: "isRunningSetter",
    get: function() {
      return D(this.flags_, e.isRunningSetterMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isRunningSetterMask_, r);
    }
  }, {
    key: "isBeingObserved",
    get: function() {
      return D(this.flags_, e.isBeingObservedMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isBeingObservedMask_, r);
    }
  }, {
    key: "isPendingUnobservation",
    get: function() {
      return D(this.flags_, e.isPendingUnobservationMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isPendingUnobservationMask_, r);
    }
  }, {
    key: "diffValue",
    get: function() {
      return D(this.flags_, e.diffValueMask_) ? 1 : 0;
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.diffValueMask_, r === 1);
    }
  }]);
}();
B.isComputingMask_ = 1;
B.isRunningSetterMask_ = 2;
B.isBeingObservedMask_ = 4;
B.isPendingUnobservationMask_ = 8;
B.diffValueMask_ = 16;
var fn = /* @__PURE__ */ ke("ComputedValue", B), _;
(function(e) {
  e[e.NOT_TRACKING_ = -1] = "NOT_TRACKING_", e[e.UP_TO_DATE_ = 0] = "UP_TO_DATE_", e[e.POSSIBLY_STALE_ = 1] = "POSSIBLY_STALE_", e[e.STALE_ = 2] = "STALE_";
})(_ || (_ = {}));
var U;
(function(e) {
  e[e.NONE = 0] = "NONE", e[e.LOG = 1] = "LOG", e[e.BREAK = 2] = "BREAK";
})(U || (U = {}));
var Kt = function(t) {
  this.cause = void 0, this.cause = t;
};
function Mt(e) {
  return e instanceof Kt;
}
function In(e) {
  switch (e.dependenciesState_) {
    case _.UP_TO_DATE_:
      return !1;
    case _.NOT_TRACKING_:
    case _.STALE_:
      return !0;
    case _.POSSIBLY_STALE_: {
      for (var t = Xn(!0), n = Te(), r = e.observing_, i = r.length, o = 0; o < i; o++) {
        var a = r[o];
        if (fn(a)) {
          if (f.disableErrorBoundaries)
            a.get();
          else
            try {
              a.get();
            } catch {
              return ae(n), vt(t), !0;
            }
          if (e.dependenciesState_ === _.STALE_)
            return ae(n), vt(t), !0;
        }
      }
      return Ni(e), ae(n), vt(t), !1;
    }
  }
}
function X(e) {
  if (process.env.NODE_ENV !== "production") {
    var t = e.observers_.size > 0;
    !f.allowStateChanges && (t || f.enforceActions === "always") && console.warn("[MobX] " + (f.enforceActions ? "Since strict-mode is enabled, changing (observed) observable values without using an action is not allowed. Tried to modify: " : "Side effects like changing state are not allowed at this point. Are you trying to modify state from, for example, a computed value or the render function of a React component? You can wrap side effects in 'runInAction' (or decorate functions with 'action') if needed. Tried to modify: ") + e.name_);
  }
}
function qa(e) {
  process.env.NODE_ENV !== "production" && !f.allowStateReads && f.observableRequiresReaction && console.warn("[mobx] Observable '" + e.name_ + "' being read outside a reactive context.");
}
function xi(e, t, n) {
  var r = Xn(!0);
  Ni(e), e.newObserving_ = new Array(
    // Reserve constant space for initial dependencies, dynamic space otherwise.
    // See https://github.com/mobxjs/mobx/pull/3833
    e.runId_ === 0 ? 100 : e.observing_.length
  ), e.unboundDepsCount_ = 0, e.runId_ = ++f.runId;
  var i = f.trackingDerivation;
  f.trackingDerivation = e, f.inBatch++;
  var o;
  if (f.disableErrorBoundaries === !0)
    o = t.call(n);
  else
    try {
      o = t.call(n);
    } catch (a) {
      o = new Kt(a);
    }
  return f.inBatch--, f.trackingDerivation = i, Wa(e), Ka(e), vt(r), o;
}
function Ka(e) {
  process.env.NODE_ENV !== "production" && e.observing_.length === 0 && (typeof e.requiresObservable_ == "boolean" ? e.requiresObservable_ : f.reactionRequiresObservable) && console.warn("[mobx] Derivation '" + e.name_ + "' is created/updated without reading any observable value.");
}
function Wa(e) {
  for (var t = e.observing_, n = e.observing_ = e.newObserving_, r = _.UP_TO_DATE_, i = 0, o = e.unboundDepsCount_, a = 0; a < o; a++) {
    var s = n[a];
    s.diffValue === 0 && (s.diffValue = 1, i !== a && (n[i] = s), i++), s.dependenciesState_ > r && (r = s.dependenciesState_);
  }
  for (n.length = i, e.newObserving_ = null, o = t.length; o--; ) {
    var l = t[o];
    l.diffValue === 0 && Ci(l, e), l.diffValue = 0;
  }
  for (; i--; ) {
    var c = n[i];
    c.diffValue === 1 && (c.diffValue = 0, Ga(c, e));
  }
  r !== _.UP_TO_DATE_ && (e.dependenciesState_ = r, e.onBecomeStale_());
}
function Vn(e) {
  var t = e.observing_;
  e.observing_ = [];
  for (var n = t.length; n--; )
    Ci(t[n], e);
  e.dependenciesState_ = _.NOT_TRACKING_;
}
function Si(e) {
  var t = Te();
  try {
    return e();
  } finally {
    ae(t);
  }
}
function Te() {
  var e = f.trackingDerivation;
  return f.trackingDerivation = null, e;
}
function ae(e) {
  f.trackingDerivation = e;
}
function Xn(e) {
  var t = f.allowStateReads;
  return f.allowStateReads = e, t;
}
function vt(e) {
  f.allowStateReads = e;
}
function Ni(e) {
  if (e.dependenciesState_ !== _.UP_TO_DATE_) {
    e.dependenciesState_ = _.UP_TO_DATE_;
    for (var t = e.observing_, n = t.length; n--; )
      t[n].lowestObserverState_ = _.UP_TO_DATE_;
  }
}
var bn = function() {
  this.version = 6, this.UNCHANGED = {}, this.trackingDerivation = null, this.trackingContext = null, this.runId = 0, this.mobxGuid = 0, this.inBatch = 0, this.pendingUnobservations = [], this.pendingReactions = [], this.isRunningReactions = !1, this.allowStateChanges = !1, this.allowStateReads = !0, this.enforceActions = !0, this.spyListeners = [], this.globalReactionErrorHandlers = [], this.computedRequiresReaction = !1, this.reactionRequiresObservable = !1, this.observableRequiresReaction = !1, this.disableErrorBoundaries = !1, this.suppressReactionErrors = !1, this.useProxies = !0, this.verifyProxies = !1, this.safeDescriptors = !0;
}, _n = !0, f = /* @__PURE__ */ function() {
  var e = /* @__PURE__ */ Kn();
  return e.__mobxInstanceCount > 0 && !e.__mobxGlobals && (_n = !1), e.__mobxGlobals && e.__mobxGlobals.version !== new bn().version && (_n = !1), _n ? e.__mobxGlobals ? (e.__mobxInstanceCount += 1, e.__mobxGlobals.UNCHANGED || (e.__mobxGlobals.UNCHANGED = {}), e.__mobxGlobals) : (e.__mobxInstanceCount = 1, e.__mobxGlobals = /* @__PURE__ */ new bn()) : (setTimeout(function() {
    p(35);
  }, 1), new bn());
}();
function Ga(e, t) {
  e.observers_.add(t), e.lowestObserverState_ > t.dependenciesState_ && (e.lowestObserverState_ = t.dependenciesState_);
}
function Ci(e, t) {
  e.observers_.delete(t), e.observers_.size === 0 && Pi(e);
}
function Pi(e) {
  e.isPendingUnobservation === !1 && (e.isPendingUnobservation = !0, f.pendingUnobservations.push(e));
}
function L() {
  f.inBatch++;
}
function z() {
  if (--f.inBatch === 0) {
    Ii();
    for (var e = f.pendingUnobservations, t = 0; t < e.length; t++) {
      var n = e[t];
      n.isPendingUnobservation = !1, n.observers_.size === 0 && (n.isBeingObserved && (n.isBeingObserved = !1, n.onBUO()), n instanceof B && n.suspend_());
    }
    f.pendingUnobservations = [];
  }
}
function $i(e) {
  qa(e);
  var t = f.trackingDerivation;
  return t !== null ? (t.runId_ !== e.lastAccessedBy_ && (e.lastAccessedBy_ = t.runId_, t.newObserving_[t.unboundDepsCount_++] = e, !e.isBeingObserved && f.trackingContext && (e.isBeingObserved = !0, e.onBO())), e.isBeingObserved) : (e.observers_.size === 0 && f.inBatch > 0 && Pi(e), !1);
}
function Di(e) {
  e.lowestObserverState_ !== _.STALE_ && (e.lowestObserverState_ = _.STALE_, e.observers_.forEach(function(t) {
    t.dependenciesState_ === _.UP_TO_DATE_ && (process.env.NODE_ENV !== "production" && t.isTracing_ !== U.NONE && ki(t, e), t.onBecomeStale_()), t.dependenciesState_ = _.STALE_;
  }));
}
function Ya(e) {
  e.lowestObserverState_ !== _.STALE_ && (e.lowestObserverState_ = _.STALE_, e.observers_.forEach(function(t) {
    t.dependenciesState_ === _.POSSIBLY_STALE_ ? (t.dependenciesState_ = _.STALE_, process.env.NODE_ENV !== "production" && t.isTracing_ !== U.NONE && ki(t, e)) : t.dependenciesState_ === _.UP_TO_DATE_ && (e.lowestObserverState_ = _.UP_TO_DATE_);
  }));
}
function Ja(e) {
  e.lowestObserverState_ === _.UP_TO_DATE_ && (e.lowestObserverState_ = _.POSSIBLY_STALE_, e.observers_.forEach(function(t) {
    t.dependenciesState_ === _.UP_TO_DATE_ && (t.dependenciesState_ = _.POSSIBLY_STALE_, t.onBecomeStale_());
  }));
}
function ki(e, t) {
  if (console.log("[mobx.trace] '" + e.name_ + "' is invalidated due to a change in: '" + t.name_ + "'"), e.isTracing_ === U.BREAK) {
    var n = [];
    Ti(ds(e), n, 1), new Function(`debugger;
/*
Tracing '` + e.name_ + `'

You are entering this break point because derivation '` + e.name_ + "' is being traced and '" + t.name_ + `' is now forcing it to update.
Just follow the stacktrace you should now see in the devtools to see precisely what piece of your code is causing this update
The stackframe you are looking for is at least ~6-8 stack-frames up.

` + (e instanceof B ? e.derivation.toString().replace(/[*]\//g, "/") : "") + `

The dependencies for this derivation are:

` + n.join(`
`) + `
*/
    `)();
  }
}
function Ti(e, t, n) {
  if (t.length >= 1e3) {
    t.push("(and many more)");
    return;
  }
  t.push("" + "	".repeat(n - 1) + e.name), e.dependencies && e.dependencies.forEach(function(r) {
    return Ti(r, t, n + 1);
  });
}
var ee = /* @__PURE__ */ function() {
  function e(n, r, i, o) {
    n === void 0 && (n = process.env.NODE_ENV !== "production" ? "Reaction@" + F() : "Reaction"), this.name_ = void 0, this.onInvalidate_ = void 0, this.errorHandler_ = void 0, this.requiresObservable_ = void 0, this.observing_ = [], this.newObserving_ = [], this.dependenciesState_ = _.NOT_TRACKING_, this.runId_ = 0, this.unboundDepsCount_ = 0, this.flags_ = 0, this.isTracing_ = U.NONE, this.name_ = n, this.onInvalidate_ = r, this.errorHandler_ = i, this.requiresObservable_ = o;
  }
  var t = e.prototype;
  return t.onBecomeStale_ = function() {
    this.schedule_();
  }, t.schedule_ = function() {
    this.isScheduled || (this.isScheduled = !0, f.pendingReactions.push(this), Ii());
  }, t.runReaction_ = function() {
    if (!this.isDisposed) {
      L(), this.isScheduled = !1;
      var r = f.trackingContext;
      if (f.trackingContext = this, In(this)) {
        this.isTrackPending = !0;
        try {
          this.onInvalidate_(), process.env.NODE_ENV !== "production" && this.isTrackPending && C() && Pe({
            name: this.name_,
            type: "scheduled-reaction"
          });
        } catch (i) {
          this.reportExceptionInDerivation_(i);
        }
      }
      f.trackingContext = r, z();
    }
  }, t.track = function(r) {
    if (!this.isDisposed) {
      L();
      var i = C(), o;
      process.env.NODE_ENV !== "production" && i && (o = Date.now(), T({
        name: this.name_,
        type: "reaction"
      })), this.isRunning = !0;
      var a = f.trackingContext;
      f.trackingContext = this;
      var s = xi(this, r, void 0);
      f.trackingContext = a, this.isRunning = !1, this.isTrackPending = !1, this.isDisposed && Vn(this), Mt(s) && this.reportExceptionInDerivation_(s.cause), process.env.NODE_ENV !== "production" && i && I({
        time: Date.now() - o
      }), z();
    }
  }, t.reportExceptionInDerivation_ = function(r) {
    var i = this;
    if (this.errorHandler_) {
      this.errorHandler_(r, this);
      return;
    }
    if (f.disableErrorBoundaries)
      throw r;
    var o = process.env.NODE_ENV !== "production" ? "[mobx] Encountered an uncaught exception that was thrown by a reaction or observer component, in: '" + this + "'" : "[mobx] uncaught error in '" + this + "'";
    f.suppressReactionErrors ? process.env.NODE_ENV !== "production" && console.warn("[mobx] (error in reaction '" + this.name_ + "' suppressed, fix error of causing action below)") : console.error(o, r), process.env.NODE_ENV !== "production" && C() && Pe({
      type: "error",
      name: this.name_,
      message: o,
      error: "" + r
    }), f.globalReactionErrorHandlers.forEach(function(a) {
      return a(r, i);
    });
  }, t.dispose = function() {
    this.isDisposed || (this.isDisposed = !0, this.isRunning || (L(), Vn(this), z()));
  }, t.getDisposer_ = function(r) {
    var i = this, o = function a() {
      i.dispose(), r == null || r.removeEventListener == null || r.removeEventListener("abort", a);
    };
    return r == null || r.addEventListener == null || r.addEventListener("abort", o), o[m] = this, o;
  }, t.toString = function() {
    return "Reaction[" + this.name_ + "]";
  }, t.trace = function(r) {
    r === void 0 && (r = !1), gs(this, r);
  }, Xe(e, [{
    key: "isDisposed",
    get: function() {
      return D(this.flags_, e.isDisposedMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isDisposedMask_, r);
    }
  }, {
    key: "isScheduled",
    get: function() {
      return D(this.flags_, e.isScheduledMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isScheduledMask_, r);
    }
  }, {
    key: "isTrackPending",
    get: function() {
      return D(this.flags_, e.isTrackPendingMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isTrackPendingMask_, r);
    }
  }, {
    key: "isRunning",
    get: function() {
      return D(this.flags_, e.isRunningMask_);
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.isRunningMask_, r);
    }
  }, {
    key: "diffValue",
    get: function() {
      return D(this.flags_, e.diffValueMask_) ? 1 : 0;
    },
    set: function(r) {
      this.flags_ = k(this.flags_, e.diffValueMask_, r === 1);
    }
  }]);
}();
ee.isDisposedMask_ = 1;
ee.isScheduledMask_ = 2;
ee.isTrackPendingMask_ = 4;
ee.isRunningMask_ = 8;
ee.diffValueMask_ = 16;
function Xa(e) {
  return f.globalReactionErrorHandlers.push(e), function() {
    var t = f.globalReactionErrorHandlers.indexOf(e);
    t >= 0 && f.globalReactionErrorHandlers.splice(t, 1);
  };
}
var pr = 100, Za = function(t) {
  return t();
};
function Ii() {
  f.inBatch > 0 || f.isRunningReactions || Za(Qa);
}
function Qa() {
  f.isRunningReactions = !0;
  for (var e = f.pendingReactions, t = 0; e.length > 0; ) {
    ++t === pr && (console.error(process.env.NODE_ENV !== "production" ? "Reaction doesn't converge to a stable state after " + pr + " iterations." + (" Probably there is a cycle in the reactive function: " + e[0]) : "[mobx] cycle in reaction: " + e[0]), e.splice(0));
    for (var n = e.splice(0), r = 0, i = n.length; r < i; r++)
      n[r].runReaction_();
  }
  f.isRunningReactions = !1;
}
var Wt = /* @__PURE__ */ ke("Reaction", ee);
function C() {
  return process.env.NODE_ENV !== "production" && !!f.spyListeners.length;
}
function Pe(e) {
  if (process.env.NODE_ENV !== "production" && f.spyListeners.length)
    for (var t = f.spyListeners, n = 0, r = t.length; n < r; n++)
      t[n](e);
}
function T(e) {
  if (process.env.NODE_ENV !== "production") {
    var t = ve({}, e, {
      spyReportStart: !0
    });
    Pe(t);
  }
}
var es = {
  type: "report-end",
  spyReportEnd: !0
};
function I(e) {
  process.env.NODE_ENV !== "production" && Pe(e ? ve({}, e, {
    type: "report-end",
    spyReportEnd: !0
  }) : es);
}
function ts(e) {
  return process.env.NODE_ENV === "production" ? (console.warn("[mobx.spy] Is a no-op in production builds"), function() {
  }) : (f.spyListeners.push(e), Gn(function() {
    f.spyListeners = f.spyListeners.filter(function(t) {
      return t !== e;
    });
  }));
}
var Zn = "action", ns = "action.bound", Vi = "autoAction", rs = "autoAction.bound", Ri = "<unnamed action>", Rn = /* @__PURE__ */ Nt(Zn), is = /* @__PURE__ */ Nt(ns, {
  bound: !0
}), jn = /* @__PURE__ */ Nt(Vi, {
  autoAction: !0
}), os = /* @__PURE__ */ Nt(rs, {
  autoAction: !0,
  bound: !0
});
function ji(e) {
  var t = function(r, i) {
    if (A(r))
      return Ce(r.name || Ri, r, e);
    if (A(i))
      return Ce(r, i, e);
    if (St(i))
      return (e ? jn : Rn).decorate_20223_(r, i);
    if (Se(i))
      return xt(r, i, e ? jn : Rn);
    if (Se(r))
      return Q(Nt(e ? Vi : Zn, {
        name: r,
        autoAction: e
      }));
    process.env.NODE_ENV !== "production" && p("Invalid arguments for `action`");
  };
  return t;
}
var Oe = /* @__PURE__ */ ji(!1);
Object.assign(Oe, Rn);
var bt = /* @__PURE__ */ ji(!0);
Object.assign(bt, jn);
Oe.bound = /* @__PURE__ */ Q(is);
bt.bound = /* @__PURE__ */ Q(os);
function bu(e) {
  return Ai(e.name || Ri, !1, e, this, void 0);
}
function qe(e) {
  return A(e) && e.isMobxAction === !0;
}
function Mi(e, t) {
  var n, r, i, o;
  t === void 0 && (t = Wn), process.env.NODE_ENV !== "production" && (A(e) || p("Autorun expects a function as first argument"), qe(e) && p("Autorun does not accept actions since actions are untrackable"));
  var a = (n = (r = t) == null ? void 0 : r.name) != null ? n : process.env.NODE_ENV !== "production" ? e.name || "Autorun@" + F() : "Autorun", s = !t.scheduler && !t.delay, l;
  if (s)
    l = new ee(a, function() {
      this.track(d);
    }, t.onError, t.requiresObservable);
  else {
    var c = Li(t), u = !1;
    l = new ee(a, function() {
      u || (u = !0, c(function() {
        u = !1, l.isDisposed || l.track(d);
      }));
    }, t.onError, t.requiresObservable);
  }
  function d() {
    e(l);
  }
  return (i = t) != null && (i = i.signal) != null && i.aborted || l.schedule_(), l.getDisposer_((o = t) == null ? void 0 : o.signal);
}
var as = function(t) {
  return t();
};
function Li(e) {
  return e.scheduler ? e.scheduler : e.delay ? function(t) {
    return setTimeout(t, e.delay);
  } : as;
}
function Qn(e, t, n) {
  var r, i, o;
  n === void 0 && (n = Wn), process.env.NODE_ENV !== "production" && ((!A(e) || !A(t)) && p("First and second argument to reaction should be functions"), P(n) || p("Third argument of reactions should be an object"));
  var a = (r = n.name) != null ? r : process.env.NODE_ENV !== "production" ? "Reaction@" + F() : "Reaction", s = Oe(a, n.onError ? ss(n.onError, t) : t), l = !n.scheduler && !n.delay, c = Li(n), u = !0, d = !1, v, h = n.compareStructural ? He.structural : n.equals || He.default, b = new ee(a, function() {
    u || l ? E() : d || (d = !0, c(E));
  }, n.onError, n.requiresObservable);
  function E() {
    if (d = !1, !b.isDisposed) {
      var S = !1, Y = v;
      b.track(function() {
        var je = Fa(!1, function() {
          return e(b);
        });
        S = u || !h(v, je), v = je;
      }), (u && n.fireImmediately || !u && S) && s(v, Y, b), u = !1;
    }
  }
  return (i = n) != null && (i = i.signal) != null && i.aborted || b.schedule_(), b.getDisposer_((o = n) == null ? void 0 : o.signal);
}
function ss(e, t) {
  return function() {
    try {
      return t.apply(this, arguments);
    } catch (n) {
      e.call(this, n);
    }
  };
}
var ls = "onBO", cs = "onBUO";
function us(e, t, n) {
  return Ui(ls, e, t, n);
}
function zi(e, t, n) {
  return Ui(cs, e, t, n);
}
function Ui(e, t, n, r) {
  var i = We(t), o = A(r) ? r : n, a = e + "L";
  return i[a] ? i[a].add(o) : i[a] = /* @__PURE__ */ new Set([o]), function() {
    var s = i[a];
    s && (s.delete(o), s.size === 0 && delete i[a]);
  };
}
function Bi(e, t, n, r) {
  process.env.NODE_ENV !== "production" && (arguments.length > 4 && p("'extendObservable' expected 2-4 arguments"), typeof e != "object" && p("'extendObservable' expects an object as first argument"), me(e) && p("'extendObservable' should not be used on maps, use map.merge instead"), P(t) || p("'extendObservable' only accepts plain objects as second argument"), (yt(t) || yt(n)) && p("Extending an object with another observable (object) is not supported"));
  var i = Yo(t);
  return Ie(function() {
    var o = Qe(e, r)[m];
    mt(i).forEach(function(a) {
      o.extend_(
        a,
        i[a],
        // must pass "undefined" for { key: undefined }
        n && a in n ? n[a] : !0
      );
    });
  }), e;
}
function ds(e, t) {
  return Fi(We(e, t));
}
function Fi(e) {
  var t = {
    name: e.name_
  };
  return e.observing_ && e.observing_.length > 0 && (t.dependencies = fs(e.observing_).map(Fi)), t;
}
function fs(e) {
  return Array.from(new Set(e));
}
var ps = 0;
function Hi() {
  this.message = "FLOW_CANCELLED";
}
Hi.prototype = /* @__PURE__ */ Object.create(Error.prototype);
var yn = /* @__PURE__ */ mi("flow"), vs = /* @__PURE__ */ mi("flow.bound", {
  bound: !0
}), Ke = /* @__PURE__ */ Object.assign(function(t, n) {
  if (St(n))
    return yn.decorate_20223_(t, n);
  if (Se(n))
    return xt(t, n, yn);
  process.env.NODE_ENV !== "production" && arguments.length !== 1 && p("Flow expects single argument with generator function");
  var r = t, i = r.name || "<unnamed flow>", o = function() {
    var s = this, l = arguments, c = ++ps, u = Oe(i + " - runid: " + c + " - init", r).apply(s, l), d, v = void 0, h = new Promise(function(b, E) {
      var S = 0;
      d = E;
      function Y($) {
        v = void 0;
        var ce;
        try {
          ce = Oe(i + " - runid: " + c + " - yield " + S++, u.next).call(u, $);
        } catch (be) {
          return E(be);
        }
        rt(ce);
      }
      function je($) {
        v = void 0;
        var ce;
        try {
          ce = Oe(i + " - runid: " + c + " - yield " + S++, u.throw).call(u, $);
        } catch (be) {
          return E(be);
        }
        rt(ce);
      }
      function rt($) {
        if (A($?.then)) {
          $.then(rt, E);
          return;
        }
        return $.done ? b($.value) : (v = Promise.resolve($.value), v.then(Y, je));
      }
      Y(void 0);
    });
    return h.cancel = Oe(i + " - runid: " + c + " - cancel", function() {
      try {
        v && vr(v);
        var b = u.return(void 0), E = Promise.resolve(b.value);
        E.then(ze, ze), vr(E), d(new Hi());
      } catch (S) {
        d(S);
      }
    }), h;
  };
  return o.isMobXFlow = !0, o;
}, yn);
Ke.bound = /* @__PURE__ */ Q(vs);
function vr(e) {
  A(e.cancel) && e.cancel();
}
function _t(e) {
  return e?.isMobXFlow === !0;
}
function hs(e, t) {
  return e ? et(e) || !!e[m] || Yn(e) || Wt(e) || fn(e) : !1;
}
function yt(e) {
  return process.env.NODE_ENV !== "production" && arguments.length !== 1 && p("isObservable expects only 1 argument. Use isObservableProp to inspect the observability of a property"), hs(e);
}
function gs() {
  if (process.env.NODE_ENV !== "production") {
    for (var e = !1, t = arguments.length, n = new Array(t), r = 0; r < t; r++)
      n[r] = arguments[r];
    typeof n[n.length - 1] == "boolean" && (e = n.pop());
    var i = ms(n);
    if (!i)
      return p("'trace(break?)' can only be used inside a tracked computed value or a Reaction. Consider passing in the computed value or reaction explicitly");
    i.isTracing_ === U.NONE && console.log("[mobx.trace] '" + i.name_ + "' tracing enabled"), i.isTracing_ = e ? U.BREAK : U.LOG;
  }
}
function ms(e) {
  switch (e.length) {
    case 0:
      return f.trackingDerivation;
    case 1:
      return We(e[0]);
    case 2:
      return We(e[0], e[1]);
  }
}
function oe(e, t) {
  t === void 0 && (t = void 0), L();
  try {
    return e.apply(t);
  } finally {
    z();
  }
}
function _e(e) {
  return e[m];
}
var bs = {
  has: function(t, n) {
    return process.env.NODE_ENV !== "production" && f.trackingDerivation && it("detect new properties using the 'in' operator. Use 'has' from 'mobx' instead."), _e(t).has_(n);
  },
  get: function(t, n) {
    return _e(t).get_(n);
  },
  set: function(t, n, r) {
    var i;
    return Se(n) ? (process.env.NODE_ENV !== "production" && !_e(t).values_.has(n) && it("add a new observable property through direct assignment. Use 'set' from 'mobx' instead."), (i = _e(t).set_(n, r, !0)) != null ? i : !0) : !1;
  },
  deleteProperty: function(t, n) {
    var r;
    return process.env.NODE_ENV !== "production" && it("delete properties from an observable object. Use 'remove' from 'mobx' instead."), Se(n) ? (r = _e(t).delete_(n, !0)) != null ? r : !0 : !1;
  },
  defineProperty: function(t, n, r) {
    var i;
    return process.env.NODE_ENV !== "production" && it("define property on an observable object. Use 'defineProperty' from 'mobx' instead."), (i = _e(t).defineProperty_(n, r)) != null ? i : !0;
  },
  ownKeys: function(t) {
    return process.env.NODE_ENV !== "production" && f.trackingDerivation && it("iterate keys to detect added / removed properties. Use 'keys' from 'mobx' instead."), _e(t).ownKeys_();
  },
  preventExtensions: function(t) {
    p(13);
  }
};
function _s(e, t) {
  var n, r;
  return ci(), e = Qe(e, t), (r = (n = e[m]).proxy_) != null ? r : n.proxy_ = new Proxy(e, bs);
}
function j(e) {
  return e.interceptors_ !== void 0 && e.interceptors_.length > 0;
}
function Ct(e, t) {
  var n = e.interceptors_ || (e.interceptors_ = []);
  return n.push(t), Gn(function() {
    var r = n.indexOf(t);
    r !== -1 && n.splice(r, 1);
  });
}
function M(e, t) {
  var n = Te();
  try {
    for (var r = [].concat(e.interceptors_ || []), i = 0, o = r.length; i < o && (t = r[i](t), t && !t.type && p(14), !!t); i++)
      ;
    return t;
  } finally {
    ae(n);
  }
}
function K(e) {
  return e.changeListeners_ !== void 0 && e.changeListeners_.length > 0;
}
function Pt(e, t) {
  var n = e.changeListeners_ || (e.changeListeners_ = []);
  return n.push(t), Gn(function() {
    var r = n.indexOf(t);
    r !== -1 && n.splice(r, 1);
  });
}
function W(e, t) {
  var n = Te(), r = e.changeListeners_;
  if (r) {
    r = r.slice();
    for (var i = 0, o = r.length; i < o; i++)
      r[i](t);
    ae(n);
  }
}
var wn = /* @__PURE__ */ Symbol("mobx-keys");
function Ze(e, t, n) {
  return process.env.NODE_ENV !== "production" && (!P(e) && !P(Object.getPrototypeOf(e)) && p("'makeAutoObservable' can only be used for classes that don't have a superclass"), et(e) && p("makeAutoObservable can only be used on objects not already made observable")), P(e) ? Bi(e, e, t, n) : (Ie(function() {
    var r = Qe(e, n)[m];
    if (!e[wn]) {
      var i = Object.getPrototypeOf(e), o = new Set([].concat(mt(e), mt(i)));
      o.delete("constructor"), o.delete(m), on(i, wn, o);
    }
    e[wn].forEach(function(a) {
      return r.make_(
        a,
        // must pass "undefined" for { key: undefined }
        t && a in t ? t[a] : !0
      );
    });
  }), e);
}
var hr = "splice", H = "update", ys = 1e4, ws = {
  get: function(t, n) {
    var r = t[m];
    return n === m ? r : n === "length" ? r.getArrayLength_() : typeof n == "string" && !isNaN(n) ? r.get_(parseInt(n)) : q(Gt, n) ? Gt[n] : t[n];
  },
  set: function(t, n, r) {
    var i = t[m];
    return n === "length" && i.setArrayLength_(r), typeof n == "symbol" || isNaN(n) ? t[n] = r : i.set_(parseInt(n), r), !0;
  },
  preventExtensions: function() {
    p(15);
  }
}, er = /* @__PURE__ */ function() {
  function e(n, r, i, o) {
    n === void 0 && (n = process.env.NODE_ENV !== "production" ? "ObservableArray@" + F() : "ObservableArray"), this.owned_ = void 0, this.legacyMode_ = void 0, this.atom_ = void 0, this.values_ = [], this.interceptors_ = void 0, this.changeListeners_ = void 0, this.enhancer_ = void 0, this.dehancer = void 0, this.proxy_ = void 0, this.lastKnownLength_ = 0, this.owned_ = i, this.legacyMode_ = o, this.atom_ = new ge(n), this.enhancer_ = function(a, s) {
      return r(a, s, process.env.NODE_ENV !== "production" ? n + "[..]" : "ObservableArray[..]");
    };
  }
  var t = e.prototype;
  return t.dehanceValue_ = function(r) {
    return this.dehancer !== void 0 ? this.dehancer(r) : r;
  }, t.dehanceValues_ = function(r) {
    return this.dehancer !== void 0 && r.length > 0 ? r.map(this.dehancer) : r;
  }, t.intercept_ = function(r) {
    return Ct(this, r);
  }, t.observe_ = function(r, i) {
    return i === void 0 && (i = !1), i && r({
      observableKind: "array",
      object: this.proxy_,
      debugObjectName: this.atom_.name_,
      type: "splice",
      index: 0,
      added: this.values_.slice(),
      addedCount: this.values_.length,
      removed: [],
      removedCount: 0
    }), Pt(this, r);
  }, t.getArrayLength_ = function() {
    return this.atom_.reportObserved(), this.values_.length;
  }, t.setArrayLength_ = function(r) {
    (typeof r != "number" || isNaN(r) || r < 0) && p("Out of range: " + r);
    var i = this.values_.length;
    if (r !== i)
      if (r > i) {
        for (var o = new Array(r - i), a = 0; a < r - i; a++)
          o[a] = void 0;
        this.spliceWithArray_(i, 0, o);
      } else
        this.spliceWithArray_(r, i - r);
  }, t.updateArrayLength_ = function(r, i) {
    r !== this.lastKnownLength_ && p(16), this.lastKnownLength_ += i, this.legacyMode_ && i > 0 && Ji(r + i + 1);
  }, t.spliceWithArray_ = function(r, i, o) {
    var a = this;
    X(this.atom_);
    var s = this.values_.length;
    if (r === void 0 ? r = 0 : r > s ? r = s : r < 0 && (r = Math.max(0, s + r)), arguments.length === 1 ? i = s - r : i == null ? i = 0 : i = Math.max(0, Math.min(i, s - r)), o === void 0 && (o = Ft), j(this)) {
      var l = M(this, {
        object: this.proxy_,
        type: hr,
        index: r,
        removedCount: i,
        added: o
      });
      if (!l)
        return Ft;
      i = l.removedCount, o = l.added;
    }
    if (o = o.length === 0 ? o : o.map(function(d) {
      return a.enhancer_(d, void 0);
    }), this.legacyMode_ || process.env.NODE_ENV !== "production") {
      var c = o.length - i;
      this.updateArrayLength_(s, c);
    }
    var u = this.spliceItemsIntoValues_(r, i, o);
    return (i !== 0 || o.length !== 0) && this.notifyArraySplice_(r, o, u), this.dehanceValues_(u);
  }, t.spliceItemsIntoValues_ = function(r, i, o) {
    if (o.length < ys) {
      var a;
      return (a = this.values_).splice.apply(a, [r, i].concat(o));
    } else {
      var s = this.values_.slice(r, r + i), l = this.values_.slice(r + i);
      this.values_.length += o.length - i;
      for (var c = 0; c < o.length; c++)
        this.values_[r + c] = o[c];
      for (var u = 0; u < l.length; u++)
        this.values_[r + o.length + u] = l[u];
      return s;
    }
  }, t.notifyArrayChildUpdate_ = function(r, i, o) {
    var a = !this.owned_ && C(), s = K(this), l = s || a ? {
      observableKind: "array",
      object: this.proxy_,
      type: H,
      debugObjectName: this.atom_.name_,
      index: r,
      newValue: i,
      oldValue: o
    } : null;
    process.env.NODE_ENV !== "production" && a && T(l), this.atom_.reportChanged(), s && W(this, l), process.env.NODE_ENV !== "production" && a && I();
  }, t.notifyArraySplice_ = function(r, i, o) {
    var a = !this.owned_ && C(), s = K(this), l = s || a ? {
      observableKind: "array",
      object: this.proxy_,
      debugObjectName: this.atom_.name_,
      type: hr,
      index: r,
      removed: o,
      added: i,
      removedCount: o.length,
      addedCount: i.length
    } : null;
    process.env.NODE_ENV !== "production" && a && T(l), this.atom_.reportChanged(), s && W(this, l), process.env.NODE_ENV !== "production" && a && I();
  }, t.get_ = function(r) {
    if (this.legacyMode_ && r >= this.values_.length) {
      console.warn(process.env.NODE_ENV !== "production" ? "[mobx.array] Attempt to read an array index (" + r + ") that is out of bounds (" + this.values_.length + "). Please check length first. Out of bound indices will not be tracked by MobX" : "[mobx] Out of bounds read: " + r);
      return;
    }
    return this.atom_.reportObserved(), this.dehanceValue_(this.values_[r]);
  }, t.set_ = function(r, i) {
    var o = this.values_;
    if (this.legacyMode_ && r > o.length && p(17, r, o.length), r < o.length) {
      X(this.atom_);
      var a = o[r];
      if (j(this)) {
        var s = M(this, {
          type: H,
          object: this.proxy_,
          // since "this" is the real array we need to pass its proxy
          index: r,
          newValue: i
        });
        if (!s)
          return;
        i = s.newValue;
      }
      i = this.enhancer_(i, a);
      var l = i !== a;
      l && (o[r] = i, this.notifyArrayChildUpdate_(r, i, a));
    } else {
      for (var c = new Array(r + 1 - o.length), u = 0; u < c.length - 1; u++)
        c[u] = void 0;
      c[c.length - 1] = i, this.spliceWithArray_(o.length, 0, c);
    }
  }, e;
}();
function Es(e, t, n, r) {
  return n === void 0 && (n = process.env.NODE_ENV !== "production" ? "ObservableArray@" + F() : "ObservableArray"), r === void 0 && (r = !1), ci(), Ie(function() {
    var i = new er(n, t, r, !1);
    di(i.values_, m, i);
    var o = new Proxy(i.values_, ws);
    return i.proxy_ = o, e && e.length && i.spliceWithArray_(0, 0, e), o;
  });
}
var Gt = {
  clear: function() {
    return this.splice(0);
  },
  replace: function(t) {
    var n = this[m];
    return n.spliceWithArray_(0, n.values_.length, t);
  },
  // Used by JSON.stringify
  toJSON: function() {
    return this.slice();
  },
  /*
   * functions that do alter the internal structure of the array, (based on lib.es6.d.ts)
   * since these functions alter the inner structure of the array, the have side effects.
   * Because the have side effects, they should not be used in computed function,
   * and for that reason the do not call dependencyState.notifyObserved
   */
  splice: function(t, n) {
    for (var r = arguments.length, i = new Array(r > 2 ? r - 2 : 0), o = 2; o < r; o++)
      i[o - 2] = arguments[o];
    var a = this[m];
    switch (arguments.length) {
      case 0:
        return [];
      case 1:
        return a.spliceWithArray_(t);
      case 2:
        return a.spliceWithArray_(t, n);
    }
    return a.spliceWithArray_(t, n, i);
  },
  spliceWithArray: function(t, n, r) {
    return this[m].spliceWithArray_(t, n, r);
  },
  push: function() {
    for (var t = this[m], n = arguments.length, r = new Array(n), i = 0; i < n; i++)
      r[i] = arguments[i];
    return t.spliceWithArray_(t.values_.length, 0, r), t.values_.length;
  },
  pop: function() {
    return this.splice(Math.max(this[m].values_.length - 1, 0), 1)[0];
  },
  shift: function() {
    return this.splice(0, 1)[0];
  },
  unshift: function() {
    for (var t = this[m], n = arguments.length, r = new Array(n), i = 0; i < n; i++)
      r[i] = arguments[i];
    return t.spliceWithArray_(0, 0, r), t.values_.length;
  },
  reverse: function() {
    return f.trackingDerivation && p(37, "reverse"), this.replace(this.slice().reverse()), this;
  },
  sort: function() {
    f.trackingDerivation && p(37, "sort");
    var t = this.slice();
    return t.sort.apply(t, arguments), this.replace(t), this;
  },
  remove: function(t) {
    var n = this[m], r = n.dehanceValues_(n.values_).indexOf(t);
    return r > -1 ? (this.splice(r, 1), !0) : !1;
  }
};
w("at", V);
w("concat", V);
w("flat", V);
w("includes", V);
w("indexOf", V);
w("join", V);
w("lastIndexOf", V);
w("slice", V);
w("toString", V);
w("toLocaleString", V);
w("toSorted", V);
w("toSpliced", V);
w("with", V);
w("every", G);
w("filter", G);
w("find", G);
w("findIndex", G);
w("findLast", G);
w("findLastIndex", G);
w("flatMap", G);
w("forEach", G);
w("map", G);
w("some", G);
w("toReversed", G);
w("reduce", qi);
w("reduceRight", qi);
function w(e, t) {
  typeof Array.prototype[e] == "function" && (Gt[e] = t(e));
}
function V(e) {
  return function() {
    var t = this[m];
    t.atom_.reportObserved();
    var n = t.dehanceValues_(t.values_);
    return n[e].apply(n, arguments);
  };
}
function G(e) {
  return function(t, n) {
    var r = this, i = this[m];
    i.atom_.reportObserved();
    var o = i.dehanceValues_(i.values_);
    return o[e](function(a, s) {
      return t.call(n, a, s, r);
    });
  };
}
function qi(e) {
  return function() {
    var t = this, n = this[m];
    n.atom_.reportObserved();
    var r = n.dehanceValues_(n.values_), i = arguments[0];
    return arguments[0] = function(o, a, s) {
      return i(o, a, s, t);
    }, r[e].apply(r, arguments);
  };
}
var Os = /* @__PURE__ */ ke("ObservableArrayAdministration", er);
function pn(e) {
  return rn(e) && Os(e[m]);
}
var As = {}, de = "add", Yt = "delete", Ki = /* @__PURE__ */ function() {
  function e(n, r, i) {
    var o = this;
    r === void 0 && (r = Ne), i === void 0 && (i = process.env.NODE_ENV !== "production" ? "ObservableMap@" + F() : "ObservableMap"), this.enhancer_ = void 0, this.name_ = void 0, this[m] = As, this.data_ = void 0, this.hasMap_ = void 0, this.keysAtom_ = void 0, this.interceptors_ = void 0, this.changeListeners_ = void 0, this.dehancer = void 0, this.enhancer_ = r, this.name_ = i, A(Map) || p(18), Ie(function() {
      o.keysAtom_ = hi(process.env.NODE_ENV !== "production" ? o.name_ + ".keys()" : "ObservableMap.keys()"), o.data_ = /* @__PURE__ */ new Map(), o.hasMap_ = /* @__PURE__ */ new Map(), n && o.merge(n);
    });
  }
  var t = e.prototype;
  return t.has_ = function(r) {
    return this.data_.has(r);
  }, t.has = function(r) {
    var i = this;
    if (!f.trackingDerivation)
      return this.has_(r);
    var o = this.hasMap_.get(r);
    if (!o) {
      var a = o = new xe(this.has_(r), sn, process.env.NODE_ENV !== "production" ? this.name_ + "." + $n(r) + "?" : "ObservableMap.key?", !1);
      this.hasMap_.set(r, a), zi(a, function() {
        return i.hasMap_.delete(r);
      });
    }
    return o.get();
  }, t.set = function(r, i) {
    var o = this.has_(r);
    if (j(this)) {
      var a = M(this, {
        type: o ? H : de,
        object: this,
        newValue: i,
        name: r
      });
      if (!a)
        return this;
      i = a.newValue;
    }
    return o ? this.updateValue_(r, i) : this.addValue_(r, i), this;
  }, t.delete = function(r) {
    var i = this;
    if (X(this.keysAtom_), j(this)) {
      var o = M(this, {
        type: Yt,
        object: this,
        name: r
      });
      if (!o)
        return !1;
    }
    if (this.has_(r)) {
      var a = C(), s = K(this), l = s || a ? {
        observableKind: "map",
        debugObjectName: this.name_,
        type: Yt,
        object: this,
        oldValue: this.data_.get(r).value_,
        name: r
      } : null;
      return process.env.NODE_ENV !== "production" && a && T(l), oe(function() {
        var c;
        i.keysAtom_.reportChanged(), (c = i.hasMap_.get(r)) == null || c.setNewValue_(!1);
        var u = i.data_.get(r);
        u.setNewValue_(void 0), i.data_.delete(r);
      }), s && W(this, l), process.env.NODE_ENV !== "production" && a && I(), !0;
    }
    return !1;
  }, t.updateValue_ = function(r, i) {
    var o = this.data_.get(r);
    if (i = o.prepareNewValue_(i), i !== f.UNCHANGED) {
      var a = C(), s = K(this), l = s || a ? {
        observableKind: "map",
        debugObjectName: this.name_,
        type: H,
        object: this,
        oldValue: o.value_,
        name: r,
        newValue: i
      } : null;
      process.env.NODE_ENV !== "production" && a && T(l), o.setNewValue_(i), s && W(this, l), process.env.NODE_ENV !== "production" && a && I();
    }
  }, t.addValue_ = function(r, i) {
    var o = this;
    X(this.keysAtom_), oe(function() {
      var c, u = new xe(i, o.enhancer_, process.env.NODE_ENV !== "production" ? o.name_ + "." + $n(r) : "ObservableMap.key", !1);
      o.data_.set(r, u), i = u.value_, (c = o.hasMap_.get(r)) == null || c.setNewValue_(!0), o.keysAtom_.reportChanged();
    });
    var a = C(), s = K(this), l = s || a ? {
      observableKind: "map",
      debugObjectName: this.name_,
      type: de,
      object: this,
      name: r,
      newValue: i
    } : null;
    process.env.NODE_ENV !== "production" && a && T(l), s && W(this, l), process.env.NODE_ENV !== "production" && a && I();
  }, t.get = function(r) {
    return this.has(r) ? this.dehanceValue_(this.data_.get(r).get()) : this.dehanceValue_(void 0);
  }, t.dehanceValue_ = function(r) {
    return this.dehancer !== void 0 ? this.dehancer(r) : r;
  }, t.keys = function() {
    return this.keysAtom_.reportObserved(), this.data_.keys();
  }, t.values = function() {
    var r = this, i = this.keys();
    return gr({
      next: function() {
        var a = i.next(), s = a.done, l = a.value;
        return {
          done: s,
          value: s ? void 0 : r.get(l)
        };
      }
    });
  }, t.entries = function() {
    var r = this, i = this.keys();
    return gr({
      next: function() {
        var a = i.next(), s = a.done, l = a.value;
        return {
          done: s,
          value: s ? void 0 : [l, r.get(l)]
        };
      }
    });
  }, t[Symbol.iterator] = function() {
    return this.entries();
  }, t.forEach = function(r, i) {
    for (var o = Ue(this), a; !(a = o()).done; ) {
      var s = a.value, l = s[0], c = s[1];
      r.call(i, c, l, this);
    }
  }, t.merge = function(r) {
    var i = this;
    return me(r) && (r = new Map(r)), oe(function() {
      P(r) ? Go(r).forEach(function(o) {
        return i.set(o, r[o]);
      }) : Array.isArray(r) ? r.forEach(function(o) {
        var a = o[0], s = o[1];
        return i.set(a, s);
      }) : Je(r) ? (Wo(r) || p(19, r), r.forEach(function(o, a) {
        return i.set(a, o);
      })) : r != null && p(20, r);
    }), this;
  }, t.clear = function() {
    var r = this;
    oe(function() {
      Si(function() {
        for (var i = Ue(r.keys()), o; !(o = i()).done; ) {
          var a = o.value;
          r.delete(a);
        }
      });
    });
  }, t.replace = function(r) {
    var i = this;
    return oe(function() {
      for (var o = xs(r), a = /* @__PURE__ */ new Map(), s = !1, l = Ue(i.data_.keys()), c; !(c = l()).done; ) {
        var u = c.value;
        if (!o.has(u)) {
          var d = i.delete(u);
          if (d)
            s = !0;
          else {
            var v = i.data_.get(u);
            a.set(u, v);
          }
        }
      }
      for (var h = Ue(o.entries()), b; !(b = h()).done; ) {
        var E = b.value, S = E[0], Y = E[1], je = i.data_.has(S);
        if (i.set(S, Y), i.data_.has(S)) {
          var rt = i.data_.get(S);
          a.set(S, rt), je || (s = !0);
        }
      }
      if (!s)
        if (i.data_.size !== a.size)
          i.keysAtom_.reportChanged();
        else
          for (var $ = i.data_.keys(), ce = a.keys(), be = $.next(), lr = ce.next(); !be.done; ) {
            if (be.value !== lr.value) {
              i.keysAtom_.reportChanged();
              break;
            }
            be = $.next(), lr = ce.next();
          }
      i.data_ = a;
    }), this;
  }, t.toString = function() {
    return "[object ObservableMap]";
  }, t.toJSON = function() {
    return Array.from(this);
  }, t.observe_ = function(r, i) {
    return process.env.NODE_ENV !== "production" && i === !0 && p("`observe` doesn't support fireImmediately=true in combination with maps."), Pt(this, r);
  }, t.intercept_ = function(r) {
    return Ct(this, r);
  }, Xe(e, [{
    key: "size",
    get: function() {
      return this.keysAtom_.reportObserved(), this.data_.size;
    }
  }, {
    key: Symbol.toStringTag,
    get: function() {
      return "Map";
    }
  }]);
}(), me = /* @__PURE__ */ ke("ObservableMap", Ki);
function gr(e) {
  return e[Symbol.toStringTag] = "MapIterator", nr(e);
}
function xs(e) {
  if (Je(e) || me(e))
    return e;
  if (Array.isArray(e))
    return new Map(e);
  if (P(e)) {
    var t = /* @__PURE__ */ new Map();
    for (var n in e)
      t.set(n, e[n]);
    return t;
  } else
    return p(21, e);
}
var Ss = {}, Wi = /* @__PURE__ */ function() {
  function e(n, r, i) {
    var o = this;
    r === void 0 && (r = Ne), i === void 0 && (i = process.env.NODE_ENV !== "production" ? "ObservableSet@" + F() : "ObservableSet"), this.name_ = void 0, this[m] = Ss, this.data_ = /* @__PURE__ */ new Set(), this.atom_ = void 0, this.changeListeners_ = void 0, this.interceptors_ = void 0, this.dehancer = void 0, this.enhancer_ = void 0, this.name_ = i, A(Set) || p(22), this.enhancer_ = function(a, s) {
      return r(a, s, i);
    }, Ie(function() {
      o.atom_ = hi(o.name_), n && o.replace(n);
    });
  }
  var t = e.prototype;
  return t.dehanceValue_ = function(r) {
    return this.dehancer !== void 0 ? this.dehancer(r) : r;
  }, t.clear = function() {
    var r = this;
    oe(function() {
      Si(function() {
        for (var i = Ue(r.data_.values()), o; !(o = i()).done; ) {
          var a = o.value;
          r.delete(a);
        }
      });
    });
  }, t.forEach = function(r, i) {
    for (var o = Ue(this), a; !(a = o()).done; ) {
      var s = a.value;
      r.call(i, s, s, this);
    }
  }, t.add = function(r) {
    var i = this;
    if (X(this.atom_), j(this)) {
      var o = M(this, {
        type: de,
        object: this,
        newValue: r
      });
      if (!o)
        return this;
      r = o.newValue;
    }
    if (!this.has(r)) {
      oe(function() {
        i.data_.add(i.enhancer_(r, void 0)), i.atom_.reportChanged();
      });
      var a = process.env.NODE_ENV !== "production" && C(), s = K(this), l = s || a ? {
        observableKind: "set",
        debugObjectName: this.name_,
        type: de,
        object: this,
        newValue: r
      } : null;
      a && process.env.NODE_ENV !== "production" && T(l), s && W(this, l), a && process.env.NODE_ENV !== "production" && I();
    }
    return this;
  }, t.delete = function(r) {
    var i = this;
    if (j(this)) {
      var o = M(this, {
        type: Yt,
        object: this,
        oldValue: r
      });
      if (!o)
        return !1;
    }
    if (this.has(r)) {
      var a = process.env.NODE_ENV !== "production" && C(), s = K(this), l = s || a ? {
        observableKind: "set",
        debugObjectName: this.name_,
        type: Yt,
        object: this,
        oldValue: r
      } : null;
      return a && process.env.NODE_ENV !== "production" && T(l), oe(function() {
        i.atom_.reportChanged(), i.data_.delete(r);
      }), s && W(this, l), a && process.env.NODE_ENV !== "production" && I(), !0;
    }
    return !1;
  }, t.has = function(r) {
    return this.atom_.reportObserved(), this.data_.has(this.dehanceValue_(r));
  }, t.entries = function() {
    var r = this.values();
    return mr({
      next: function() {
        var o = r.next(), a = o.value, s = o.done;
        return s ? {
          value: void 0,
          done: s
        } : {
          value: [a, a],
          done: s
        };
      }
    });
  }, t.keys = function() {
    return this.values();
  }, t.values = function() {
    this.atom_.reportObserved();
    var r = this, i = this.data_.values();
    return mr({
      next: function() {
        var a = i.next(), s = a.value, l = a.done;
        return l ? {
          value: void 0,
          done: l
        } : {
          value: r.dehanceValue_(s),
          done: l
        };
      }
    });
  }, t.intersection = function(r) {
    if (re(r) && !J(r))
      return r.intersection(this);
    var i = new Set(this);
    return i.intersection(r);
  }, t.union = function(r) {
    if (re(r) && !J(r))
      return r.union(this);
    var i = new Set(this);
    return i.union(r);
  }, t.difference = function(r) {
    return new Set(this).difference(r);
  }, t.symmetricDifference = function(r) {
    if (re(r) && !J(r))
      return r.symmetricDifference(this);
    var i = new Set(this);
    return i.symmetricDifference(r);
  }, t.isSubsetOf = function(r) {
    return new Set(this).isSubsetOf(r);
  }, t.isSupersetOf = function(r) {
    return new Set(this).isSupersetOf(r);
  }, t.isDisjointFrom = function(r) {
    if (re(r) && !J(r))
      return r.isDisjointFrom(this);
    var i = new Set(this);
    return i.isDisjointFrom(r);
  }, t.replace = function(r) {
    var i = this;
    return J(r) && (r = new Set(r)), oe(function() {
      Array.isArray(r) ? (i.clear(), r.forEach(function(o) {
        return i.add(o);
      })) : re(r) ? (i.clear(), r.forEach(function(o) {
        return i.add(o);
      })) : r != null && p("Cannot initialize set from " + r);
    }), this;
  }, t.observe_ = function(r, i) {
    return process.env.NODE_ENV !== "production" && i === !0 && p("`observe` doesn't support fireImmediately=true in combination with sets."), Pt(this, r);
  }, t.intercept_ = function(r) {
    return Ct(this, r);
  }, t.toJSON = function() {
    return Array.from(this);
  }, t.toString = function() {
    return "[object ObservableSet]";
  }, t[Symbol.iterator] = function() {
    return this.values();
  }, Xe(e, [{
    key: "size",
    get: function() {
      return this.atom_.reportObserved(), this.data_.size;
    }
  }, {
    key: Symbol.toStringTag,
    get: function() {
      return "Set";
    }
  }]);
}(), J = /* @__PURE__ */ ke("ObservableSet", Wi);
function mr(e) {
  return e[Symbol.toStringTag] = "SetIterator", nr(e);
}
var br = /* @__PURE__ */ Object.create(null), _r = "remove", Mn = /* @__PURE__ */ function() {
  function e(n, r, i, o) {
    r === void 0 && (r = /* @__PURE__ */ new Map()), o === void 0 && (o = Oa), this.target_ = void 0, this.values_ = void 0, this.name_ = void 0, this.defaultAnnotation_ = void 0, this.keysAtom_ = void 0, this.changeListeners_ = void 0, this.interceptors_ = void 0, this.proxy_ = void 0, this.isPlainObject_ = void 0, this.appliedAnnotations_ = void 0, this.pendingKeys_ = void 0, this.target_ = n, this.values_ = r, this.name_ = i, this.defaultAnnotation_ = o, this.keysAtom_ = new ge(process.env.NODE_ENV !== "production" ? this.name_ + ".keys" : "ObservableObject.keys"), this.isPlainObject_ = P(this.target_), process.env.NODE_ENV !== "production" && !Qi(this.defaultAnnotation_) && p("defaultAnnotation must be valid annotation"), process.env.NODE_ENV !== "production" && (this.appliedAnnotations_ = {});
  }
  var t = e.prototype;
  return t.getObservablePropValue_ = function(r) {
    return this.values_.get(r).get();
  }, t.setObservablePropValue_ = function(r, i) {
    var o = this.values_.get(r);
    if (o instanceof B)
      return o.set(i), !0;
    if (j(this)) {
      var a = M(this, {
        type: H,
        object: this.proxy_ || this.target_,
        name: r,
        newValue: i
      });
      if (!a)
        return null;
      i = a.newValue;
    }
    if (i = o.prepareNewValue_(i), i !== f.UNCHANGED) {
      var s = K(this), l = process.env.NODE_ENV !== "production" && C(), c = s || l ? {
        type: H,
        observableKind: "object",
        debugObjectName: this.name_,
        object: this.proxy_ || this.target_,
        oldValue: o.value_,
        name: r,
        newValue: i
      } : null;
      process.env.NODE_ENV !== "production" && l && T(c), o.setNewValue_(i), s && W(this, c), process.env.NODE_ENV !== "production" && l && I();
    }
    return !0;
  }, t.get_ = function(r) {
    return f.trackingDerivation && !q(this.target_, r) && this.has_(r), this.target_[r];
  }, t.set_ = function(r, i, o) {
    return o === void 0 && (o = !1), q(this.target_, r) ? this.values_.has(r) ? this.setObservablePropValue_(r, i) : o ? Reflect.set(this.target_, r, i) : (this.target_[r] = i, !0) : this.extend_(r, {
      value: i,
      enumerable: !0,
      writable: !0,
      configurable: !0
    }, this.defaultAnnotation_, o);
  }, t.has_ = function(r) {
    if (!f.trackingDerivation)
      return r in this.target_;
    this.pendingKeys_ || (this.pendingKeys_ = /* @__PURE__ */ new Map());
    var i = this.pendingKeys_.get(r);
    return i || (i = new xe(r in this.target_, sn, process.env.NODE_ENV !== "production" ? this.name_ + "." + $n(r) + "?" : "ObservableObject.key?", !1), this.pendingKeys_.set(r, i)), i.get();
  }, t.make_ = function(r, i) {
    if (i === !0 && (i = this.defaultAnnotation_), i !== !1) {
      if (Er(this, i, r), !(r in this.target_)) {
        var o;
        if ((o = this.target_[ie]) != null && o[r])
          return;
        p(1, i.annotationType_, this.name_ + "." + r.toString());
      }
      for (var a = this.target_; a && a !== nn; ) {
        var s = Bt(a, r);
        if (s) {
          var l = i.make_(this, r, s, a);
          if (l === 0)
            return;
          if (l === 1)
            break;
        }
        a = Object.getPrototypeOf(a);
      }
      wr(this, i, r);
    }
  }, t.extend_ = function(r, i, o, a) {
    if (a === void 0 && (a = !1), o === !0 && (o = this.defaultAnnotation_), o === !1)
      return this.defineProperty_(r, i, a);
    Er(this, o, r);
    var s = o.extend_(this, r, i, a);
    return s && wr(this, o, r), s;
  }, t.defineProperty_ = function(r, i, o) {
    o === void 0 && (o = !1), X(this.keysAtom_);
    try {
      L();
      var a = this.delete_(r);
      if (!a)
        return a;
      if (j(this)) {
        var s = M(this, {
          object: this.proxy_ || this.target_,
          name: r,
          type: de,
          newValue: i.value
        });
        if (!s)
          return null;
        var l = s.newValue;
        i.value !== l && (i = ve({}, i, {
          value: l
        }));
      }
      if (o) {
        if (!Reflect.defineProperty(this.target_, r, i))
          return !1;
      } else
        Z(this.target_, r, i);
      this.notifyPropertyAddition_(r, i.value);
    } finally {
      z();
    }
    return !0;
  }, t.defineObservableProperty_ = function(r, i, o, a) {
    a === void 0 && (a = !1), X(this.keysAtom_);
    try {
      L();
      var s = this.delete_(r);
      if (!s)
        return s;
      if (j(this)) {
        var l = M(this, {
          object: this.proxy_ || this.target_,
          name: r,
          type: de,
          newValue: i
        });
        if (!l)
          return null;
        i = l.newValue;
      }
      var c = yr(r), u = {
        configurable: f.safeDescriptors ? this.isPlainObject_ : !0,
        enumerable: !0,
        get: c.get,
        set: c.set
      };
      if (a) {
        if (!Reflect.defineProperty(this.target_, r, u))
          return !1;
      } else
        Z(this.target_, r, u);
      var d = new xe(i, o, process.env.NODE_ENV !== "production" ? this.name_ + "." + r.toString() : "ObservableObject.key", !1);
      this.values_.set(r, d), this.notifyPropertyAddition_(r, d.value_);
    } finally {
      z();
    }
    return !0;
  }, t.defineComputedProperty_ = function(r, i, o) {
    o === void 0 && (o = !1), X(this.keysAtom_);
    try {
      L();
      var a = this.delete_(r);
      if (!a)
        return a;
      if (j(this)) {
        var s = M(this, {
          object: this.proxy_ || this.target_,
          name: r,
          type: de,
          newValue: void 0
        });
        if (!s)
          return null;
      }
      i.name || (i.name = process.env.NODE_ENV !== "production" ? this.name_ + "." + r.toString() : "ObservableObject.key"), i.context = this.proxy_ || this.target_;
      var l = yr(r), c = {
        configurable: f.safeDescriptors ? this.isPlainObject_ : !0,
        enumerable: !1,
        get: l.get,
        set: l.set
      };
      if (o) {
        if (!Reflect.defineProperty(this.target_, r, c))
          return !1;
      } else
        Z(this.target_, r, c);
      this.values_.set(r, new B(i)), this.notifyPropertyAddition_(r, void 0);
    } finally {
      z();
    }
    return !0;
  }, t.delete_ = function(r, i) {
    if (i === void 0 && (i = !1), X(this.keysAtom_), !q(this.target_, r))
      return !0;
    if (j(this)) {
      var o = M(this, {
        object: this.proxy_ || this.target_,
        name: r,
        type: _r
      });
      if (!o)
        return null;
    }
    try {
      var a;
      L();
      var s = K(this), l = process.env.NODE_ENV !== "production" && C(), c = this.values_.get(r), u = void 0;
      if (!c && (s || l)) {
        var d;
        u = (d = Bt(this.target_, r)) == null ? void 0 : d.value;
      }
      if (i) {
        if (!Reflect.deleteProperty(this.target_, r))
          return !1;
      } else
        delete this.target_[r];
      if (process.env.NODE_ENV !== "production" && delete this.appliedAnnotations_[r], c && (this.values_.delete(r), c instanceof xe && (u = c.value_), Di(c)), this.keysAtom_.reportChanged(), (a = this.pendingKeys_) == null || (a = a.get(r)) == null || a.set(r in this.target_), s || l) {
        var v = {
          type: _r,
          observableKind: "object",
          object: this.proxy_ || this.target_,
          debugObjectName: this.name_,
          oldValue: u,
          name: r
        };
        process.env.NODE_ENV !== "production" && l && T(v), s && W(this, v), process.env.NODE_ENV !== "production" && l && I();
      }
    } finally {
      z();
    }
    return !0;
  }, t.observe_ = function(r, i) {
    return process.env.NODE_ENV !== "production" && i === !0 && p("`observe` doesn't support the fire immediately property for observable objects."), Pt(this, r);
  }, t.intercept_ = function(r) {
    return Ct(this, r);
  }, t.notifyPropertyAddition_ = function(r, i) {
    var o, a = K(this), s = process.env.NODE_ENV !== "production" && C();
    if (a || s) {
      var l = a || s ? {
        type: de,
        observableKind: "object",
        debugObjectName: this.name_,
        object: this.proxy_ || this.target_,
        name: r,
        newValue: i
      } : null;
      process.env.NODE_ENV !== "production" && s && T(l), a && W(this, l), process.env.NODE_ENV !== "production" && s && I();
    }
    (o = this.pendingKeys_) == null || (o = o.get(r)) == null || o.set(!0), this.keysAtom_.reportChanged();
  }, t.ownKeys_ = function() {
    return this.keysAtom_.reportObserved(), mt(this.target_);
  }, t.keys_ = function() {
    return this.keysAtom_.reportObserved(), Object.keys(this.target_);
  }, e;
}();
function Qe(e, t) {
  var n;
  if (process.env.NODE_ENV !== "production" && t && et(e) && p("Options can't be provided for already observable objects."), q(e, m))
    return process.env.NODE_ENV !== "production" && !(Xi(e) instanceof Mn) && p("Cannot convert '" + Jt(e) + `' into observable object:
The target is already observable of different type.
Extending builtins is not supported.`), e;
  process.env.NODE_ENV !== "production" && !Object.isExtensible(e) && p("Cannot make the designated object observable; it is not extensible");
  var r = (n = t?.name) != null ? n : process.env.NODE_ENV !== "production" ? (P(e) ? "ObservableObject" : e.constructor.name) + "@" + F() : "ObservableObject", i = new Mn(e, /* @__PURE__ */ new Map(), String(r), Ia(t));
  return on(e, m, i), e;
}
var Ns = /* @__PURE__ */ ke("ObservableObjectAdministration", Mn);
function yr(e) {
  return br[e] || (br[e] = {
    get: function() {
      return this[m].getObservablePropValue_(e);
    },
    set: function(n) {
      return this[m].setObservablePropValue_(e, n);
    }
  });
}
function et(e) {
  return rn(e) ? Ns(e[m]) : !1;
}
function wr(e, t, n) {
  var r;
  process.env.NODE_ENV !== "production" && (e.appliedAnnotations_[n] = t), (r = e.target_[ie]) == null || delete r[n];
}
function Er(e, t, n) {
  if (process.env.NODE_ENV !== "production" && !Qi(t) && p("Cannot annotate '" + e.name_ + "." + n.toString() + "': Invalid annotation."), process.env.NODE_ENV !== "production" && !Ht(t) && q(e.appliedAnnotations_, n)) {
    var r = e.name_ + "." + n.toString(), i = e.appliedAnnotations_[n].annotationType_, o = t.annotationType_;
    p("Cannot apply '" + o + "' to '" + r + "':" + (`
The field is already annotated with '` + i + "'.") + `
Re-annotating fields is not allowed.
Use 'override' annotation for methods overridden by subclass.`);
  }
}
var Cs = /* @__PURE__ */ Yi(0), Ps = /* @__PURE__ */ function() {
  var e = !1, t = {};
  return Object.defineProperty(t, "0", {
    set: function() {
      e = !0;
    }
  }), Object.create(t)[0] = 1, e === !1;
}(), En = 0, Gi = function() {
};
function $s(e, t) {
  Object.setPrototypeOf ? Object.setPrototypeOf(e.prototype, t) : e.prototype.__proto__ !== void 0 ? e.prototype.__proto__ = t : e.prototype = t;
}
$s(Gi, Array.prototype);
var tr = /* @__PURE__ */ function(e) {
  function t(r, i, o, a) {
    var s;
    return o === void 0 && (o = process.env.NODE_ENV !== "production" ? "ObservableArray@" + F() : "ObservableArray"), a === void 0 && (a = !1), s = e.call(this) || this, Ie(function() {
      var l = new er(o, i, a, !0);
      l.proxy_ = s, di(s, m, l), r && r.length && s.spliceWithArray(0, 0, r), Ps && Object.defineProperty(s, "0", Cs);
    }), s;
  }
  vi(t, e);
  var n = t.prototype;
  return n.concat = function() {
    this[m].atom_.reportObserved();
    for (var i = arguments.length, o = new Array(i), a = 0; a < i; a++)
      o[a] = arguments[a];
    return Array.prototype.concat.apply(
      this.slice(),
      //@ts-ignore
      o.map(function(s) {
        return pn(s) ? s.slice() : s;
      })
    );
  }, n[Symbol.iterator] = function() {
    var r = this, i = 0;
    return nr({
      next: function() {
        return i < r.length ? {
          value: r[i++],
          done: !1
        } : {
          done: !0,
          value: void 0
        };
      }
    });
  }, Xe(t, [{
    key: "length",
    get: function() {
      return this[m].getArrayLength_();
    },
    set: function(i) {
      this[m].setArrayLength_(i);
    }
  }, {
    key: Symbol.toStringTag,
    get: function() {
      return "Array";
    }
  }]);
}(Gi);
Object.entries(Gt).forEach(function(e) {
  var t = e[0], n = e[1];
  t !== "concat" && on(tr.prototype, t, n);
});
function Yi(e) {
  return {
    enumerable: !1,
    configurable: !0,
    get: function() {
      return this[m].get_(e);
    },
    set: function(n) {
      this[m].set_(e, n);
    }
  };
}
function Ds(e) {
  Z(tr.prototype, "" + e, Yi(e));
}
function Ji(e) {
  if (e > En) {
    for (var t = En; t < e + 100; t++)
      Ds(t);
    En = e;
  }
}
Ji(1e3);
function ks(e, t, n) {
  return new tr(e, t, n);
}
function We(e, t) {
  if (typeof e == "object" && e !== null) {
    if (pn(e))
      return t !== void 0 && p(23), e[m].atom_;
    if (J(e))
      return e.atom_;
    if (me(e)) {
      if (t === void 0)
        return e.keysAtom_;
      var n = e.data_.get(t) || e.hasMap_.get(t);
      return n || p(25, t, Jt(e)), n;
    }
    if (et(e)) {
      if (!t)
        return p(26);
      var r = e[m].values_.get(t);
      return r || p(27, t, Jt(e)), r;
    }
    if (Yn(e) || fn(e) || Wt(e))
      return e;
  } else if (A(e) && Wt(e[m]))
    return e[m];
  p(28);
}
function Xi(e, t) {
  if (e || p(29), Yn(e) || fn(e) || Wt(e) || me(e) || J(e))
    return e;
  if (e[m])
    return e[m];
  p(24, e);
}
function Jt(e, t) {
  var n;
  if (t !== void 0)
    n = We(e, t);
  else {
    if (qe(e))
      return e.name;
    et(e) || me(e) || J(e) ? n = Xi(e) : n = We(e);
  }
  return n.name_;
}
function Ie(e) {
  var t = Te(), n = un(!0);
  L();
  try {
    return e();
  } finally {
    z(), dn(n), ae(t);
  }
}
var Or = nn.toString;
function Zi(e, t, n) {
  return n === void 0 && (n = -1), Ln(e, t, n);
}
function Ln(e, t, n, r, i) {
  if (e === t)
    return e !== 0 || 1 / e === 1 / t;
  if (e == null || t == null)
    return !1;
  if (e !== e)
    return t !== t;
  var o = typeof e;
  if (o !== "function" && o !== "object" && typeof t != "object")
    return !1;
  var a = Or.call(e);
  if (a !== Or.call(t))
    return !1;
  switch (a) {
    // Strings, numbers, regular expressions, dates, and booleans are compared by value.
    case "[object RegExp]":
    // RegExps are coerced to strings for comparison (Note: '' + /a/i === '/a/i')
    case "[object String]":
      return "" + e == "" + t;
    case "[object Number]":
      return +e != +e ? +t != +t : +e == 0 ? 1 / +e === 1 / t : +e == +t;
    case "[object Date]":
    case "[object Boolean]":
      return +e == +t;
    case "[object Symbol]":
      return typeof Symbol < "u" && Symbol.valueOf.call(e) === Symbol.valueOf.call(t);
    case "[object Map]":
    case "[object Set]":
      n >= 0 && n++;
      break;
  }
  e = Ar(e), t = Ar(t);
  var s = a === "[object Array]";
  if (!s) {
    if (typeof e != "object" || typeof t != "object")
      return !1;
    var l = e.constructor, c = t.constructor;
    if (l !== c && !(A(l) && l instanceof l && A(c) && c instanceof c) && "constructor" in e && "constructor" in t)
      return !1;
  }
  if (n === 0)
    return !1;
  n < 0 && (n = -1), r = r || [], i = i || [];
  for (var u = r.length; u--; )
    if (r[u] === e)
      return i[u] === t;
  if (r.push(e), i.push(t), s) {
    if (u = e.length, u !== t.length)
      return !1;
    for (; u--; )
      if (!Ln(e[u], t[u], n - 1, r, i))
        return !1;
  } else {
    var d = Object.keys(e), v = d.length;
    if (Object.keys(t).length !== v)
      return !1;
    for (var h = 0; h < v; h++) {
      var b = d[h];
      if (!(q(t, b) && Ln(e[b], t[b], n - 1, r, i)))
        return !1;
    }
  }
  return r.pop(), i.pop(), !0;
}
function Ar(e) {
  return pn(e) ? e.slice() : Je(e) || me(e) || re(e) || J(e) ? Array.from(e.entries()) : e;
}
var xr, Ts = ((xr = Kn().Iterator) == null ? void 0 : xr.prototype) || {};
function nr(e) {
  return e[Symbol.iterator] = Is, Object.assign(Object.create(Ts), e);
}
function Is() {
  return this;
}
function Qi(e) {
  return (
    // Can be function
    e instanceof Object && typeof e.annotationType_ == "string" && A(e.make_) && A(e.extend_)
  );
}
["Symbol", "Map", "Set"].forEach(function(e) {
  var t = Kn();
  typeof t[e] > "u" && p("MobX requires global '" + e + "' to be available or polyfilled");
});
typeof __MOBX_DEVTOOLS_GLOBAL_HOOK__ == "object" && __MOBX_DEVTOOLS_GLOBAL_HOOK__.injectMobx({
  spy: ts,
  extras: {
    getDebugName: Jt
  },
  $mobx: m
});
const Sr = "copilot-conf";
class fe {
  static get sessionConfiguration() {
    const t = sessionStorage.getItem(Sr);
    return t ? JSON.parse(t) : {};
  }
  static saveCopilotActivation(t) {
    const n = this.sessionConfiguration;
    n.active = t, this.persist(n);
  }
  static getCopilotActivation() {
    return this.sessionConfiguration.active;
  }
  static saveSpotlightActivation(t) {
    const n = this.sessionConfiguration;
    n.spotlightActive = t, this.persist(n);
  }
  static getSpotlightActivation() {
    return this.sessionConfiguration.spotlightActive;
  }
  static saveSpotlightPosition(t, n, r, i) {
    const o = this.sessionConfiguration;
    o.spotlightPosition = { left: t, top: n, right: r, bottom: i }, this.persist(o);
  }
  static getSpotlightPosition() {
    return this.sessionConfiguration.spotlightPosition;
  }
  static saveDrawerSize(t, n) {
    const r = this.sessionConfiguration;
    r.drawerSizes = r.drawerSizes ?? {}, r.drawerSizes[t] = n, this.persist(r);
  }
  static getDrawerSize(t) {
    const n = this.sessionConfiguration;
    if (n.drawerSizes)
      return n.drawerSizes[t];
  }
  static savePanelConfigurations(t) {
    const n = this.sessionConfiguration;
    n.sectionPanelState = t, this.persist(n);
  }
  static getPanelConfigurations() {
    return this.sessionConfiguration.sectionPanelState;
  }
  static persist(t) {
    sessionStorage.setItem(Sr, JSON.stringify(t));
  }
  static savePrompts(t) {
    const n = this.sessionConfiguration;
    n.prompts = t, this.persist(n);
  }
  static getPrompts() {
    return this.sessionConfiguration.prompts || [];
  }
  static saveCurrentSelection(t) {
    const n = this.sessionConfiguration;
    n.selection = n.selection ?? {}, n.selection && (n.selection.current = t, n.selection.location = window.location.pathname, this.persist(n));
  }
  static savePendingSelection(t) {
    const n = this.sessionConfiguration;
    n.selection = n.selection ?? {}, n.selection && (n.selection.pending = t, n.selection.location = window.location.pathname, this.persist(n));
  }
  static getCurrentSelection() {
    const t = this.sessionConfiguration.selection;
    if (t?.location === window.location.pathname)
      return t.current;
  }
  static getPendingSelection() {
    const t = this.sessionConfiguration.selection;
    if (t?.location === window.location.pathname)
      return t.pending;
  }
  static saveDrillDownContextReference(t) {
    const n = this.sessionConfiguration;
    n.drillDownContext = n.drillDownContext ?? {}, n.drillDownContext && (n.drillDownContext.location = window.location.pathname, n.drillDownContext.stack = t, this.persist(n));
  }
  static getDrillDownContextReference() {
    const t = this.sessionConfiguration;
    if (t?.drillDownContext?.location === window.location.pathname)
      return t.drillDownContext?.stack;
  }
}
var Ve = /* @__PURE__ */ ((e) => (e.INFORMATION = "information", e.WARNING = "warning", e.ERROR = "error", e))(Ve || {});
const Vs = Symbol.for("react.portal"), Rs = Symbol.for("react.fragment"), js = Symbol.for("react.strict_mode"), Ms = Symbol.for("react.profiler"), Ls = Symbol.for("react.provider"), zs = Symbol.for("react.context"), eo = Symbol.for("react.forward_ref"), Us = Symbol.for("react.suspense"), Bs = Symbol.for("react.suspense_list"), Fs = Symbol.for("react.memo"), Hs = Symbol.for("react.lazy");
function qs(e, t, n) {
  const r = e.displayName;
  if (r)
    return r;
  const i = t.displayName || t.name || "";
  return i !== "" ? `${n}(${i})` : n;
}
function Nr(e) {
  return e.displayName || "Context";
}
function Xt(e) {
  if (e === null)
    return null;
  if (typeof e == "function")
    return e.displayName || e.name || null;
  if (typeof e == "string")
    return e;
  switch (e) {
    case Rs:
      return "Fragment";
    case Vs:
      return "Portal";
    case Ms:
      return "Profiler";
    case js:
      return "StrictMode";
    case Us:
      return "Suspense";
    case Bs:
      return "SuspenseList";
  }
  if (typeof e == "object")
    switch (e.$$typeof) {
      case zs:
        return `${Nr(e)}.Consumer`;
      case Ls:
        return `${Nr(e._context)}.Provider`;
      case eo:
        return qs(e, e.render, "ForwardRef");
      case Fs:
        const t = e.displayName || null;
        return t !== null ? t : Xt(e.type) || "Memo";
      case Hs: {
        const n = e, r = n._payload, i = n._init;
        try {
          return Xt(i(r));
        } catch {
          return null;
        }
      }
    }
  return null;
}
let kt;
function _u() {
  const e = /* @__PURE__ */ new Set();
  return Array.from(document.body.querySelectorAll("*")).flatMap(Gs).filter(Ks).filter((n) => !n.fileName.includes("frontend/generated/")).forEach((n) => e.add(n.fileName)), Array.from(e);
}
function Ks(e) {
  return !!e && e.fileName;
}
function Zt(e) {
  if (!e)
    return;
  if (e._debugSource)
    return e._debugSource;
  const t = e._debugInfo?.source;
  if (t?.fileName && t?.lineNumber)
    return t;
}
function Ws(e) {
  if (e && e.type?.__debugSourceDefine)
    return e.type.__debugSourceDefine;
}
function Gs(e) {
  return Zt(Qt(e));
}
function Ys() {
  return `__reactFiber$${to()}`;
}
function Js() {
  return `__reactContainer$${to()}`;
}
function to() {
  if (!(!kt && (kt = Array.from(document.querySelectorAll("*")).flatMap((e) => Object.keys(e)).filter((e) => e.startsWith("__reactFiber$")).map((e) => e.replace("__reactFiber$", "")).find((e) => e), !kt)))
    return kt;
}
function ft(e) {
  const t = e.type;
  return t?.$$typeof === eo && !t.displayName && e.child ? ft(e.child) : Xt(e.type) ?? Xt(e.elementType) ?? "???";
}
function Xs() {
  const e = Array.from(document.querySelectorAll("body > *")).flatMap((n) => n[Js()]).find((n) => n), t = $e(e);
  return $e(t?.child);
}
function Zs(e) {
  const t = [];
  let n = $e(e.child);
  for (; n; )
    t.push(n), n = $e(n.sibling);
  return t;
}
function Qs(e) {
  return e.hasOwnProperty("entanglements") && e.hasOwnProperty("containerInfo");
}
function el(e) {
  return e.hasOwnProperty("stateNode") && e.hasOwnProperty("pendingProps");
}
function $e(e) {
  const t = e?.stateNode;
  if (t?.current && (Qs(t) || el(t)))
    return t?.current;
  if (!e)
    return;
  if (!e.alternate)
    return e;
  const n = e.alternate, r = e?.actualStartTime, i = n?.actualStartTime;
  return i !== r && i > r ? n : e;
}
function Qt(e) {
  const t = Ys(), n = $e(e[t]);
  if (Zt(n))
    return n;
  let r = n?.return || void 0;
  for (; r && !Zt(r); )
    r = r.return || void 0;
  return r;
}
function en(e) {
  if (e.stateNode?.isConnected === !0)
    return e.stateNode;
  if (e.child)
    return en(e.child);
}
function Cr(e) {
  const t = en(e);
  return t && $e(Qt(t)) === e;
}
function tl(e) {
  return typeof e.type != "function" || no(e) ? !1 : !!(Zt(e) || Ws(e));
}
function no(e) {
  if (!e)
    return !1;
  const t = e;
  return typeof e.type == "function" && t.tag === 1;
}
const vn = async (e, t, n) => window.Vaadin.copilot.comm(e, t, n), Re = "copilot-", nl = "24.8.0", rl = "undefined", yu = "attention-required", wu = "https://plugins.jetbrains.com/plugin/23758-vaadin", Eu = "https://marketplace.visualstudio.com/items?itemName=vaadin.vaadin-vscode";
function Ou(e) {
  return e === void 0 ? !1 : e.nodeId >= 0;
}
function il(e) {
  if (e.javaClass)
    return e.javaClass.substring(e.javaClass.lastIndexOf(".") + 1);
}
function On(e) {
  const t = window.Vaadin;
  if (t && t.Flow) {
    const { clients: n } = t.Flow, r = Object.keys(n);
    for (const i of r) {
      const o = n[i];
      if (o.getNodeId) {
        const a = o.getNodeId(e);
        if (a >= 0) {
          const s = o.getNodeInfo(a);
          return {
            nodeId: a,
            uiId: o.getUIId(),
            element: e,
            javaClass: s.javaClass,
            styles: s.styles,
            hiddenByServer: s.hiddenByServer
          };
        }
      }
    }
  }
}
function Au() {
  const e = window.Vaadin;
  let t;
  if (e && e.Flow) {
    const { clients: n } = e.Flow, r = Object.keys(n);
    for (const i of r) {
      const o = n[i];
      o.getUIId && (t = o.getUIId());
    }
  }
  return t;
}
function xu(e) {
  return {
    uiId: e.uiId,
    nodeId: e.nodeId
  };
}
function ol(e) {
  return e ? e.type?.type === "FlowContainer" : !1;
}
function al(e) {
  return e.localName.startsWith("flow-container");
}
function Su(e) {
  const t = e.lastIndexOf(".");
  return t < 0 ? e : e.substring(t + 1);
}
function ro(e, t) {
  const n = e();
  n ? t(n) : setTimeout(() => ro(e, t), 50);
}
async function io(e) {
  const t = e();
  if (t)
    return t;
  let n;
  const r = new Promise((o) => {
    n = o;
  }), i = setInterval(() => {
    const o = e();
    o && (clearInterval(i), n(o));
  }, 10);
  return r;
}
function oo(e) {
  return x.box(e, { deep: !1 });
}
function sl(e) {
  return e && typeof e.lastAccessedBy_ == "number";
}
function Nu(e) {
  if (e) {
    if (typeof e == "string")
      return e;
    if (!sl(e))
      throw new Error(`Expected message to be a string or an observable value but was ${JSON.stringify(e)}`);
    return e.get();
  }
}
function ll(e) {
  return Array.from(new Set(e));
}
function hn(e) {
  Promise.resolve().then(() => pc).then(({ showNotification: t }) => {
    t(e);
  });
}
function cl() {
  hn({
    type: Ve.INFORMATION,
    message: "The previous operation is still in progress. Please wait for it to finish."
  });
}
function ul(e) {
  return e.children && (e.children = e.children.filter(ul)), e.visible !== !1;
}
async function ao() {
  return io(() => {
    const e = window.Vaadin.devTools, t = e?.frontendConnection && e?.frontendConnection.status === "active";
    return e !== void 0 && t && e?.frontendConnection;
  });
}
function te(e, t) {
  ao().then((n) => n.send(e, t));
}
const dl = () => {
  te("copilot-browser-info", {
    userAgent: navigator.userAgent,
    locale: navigator.language,
    timezone: Intl.DateTimeFormat().resolvedOptions().timeZone
  });
}, wt = (e, t) => {
  te("copilot-track-event", { event: e, properties: t });
}, Cu = (e, t) => {
  wt(e, { ...t, view: "react" });
}, Pu = (e, t) => {
  wt(e, { ...t, view: "flow" });
};
class fl {
  constructor() {
    this.spotlightActive = !1, this.welcomeActive = !1, this.loginCheckActive = !1, this.userInfo = void 0, this.active = !1, this.activatedFrom = null, this.activatedAtLeastOnce = !1, this.operationInProgress = void 0, this.operationWaitsHmrUpdate = void 0, this.operationWaitsHmrUpdateTimeout = void 0, this.idePluginState = void 0, this.notifications = [], this.infoTooltip = null, this.sectionPanelDragging = !1, this.spotlightDragging = !1, this.sectionPanelResizing = !1, this.drawerResizing = !1, this.jdkInfo = void 0, this.featureFlags = [], this.newVaadinVersionState = void 0, this.pointerEventsDisabledForScrolling = !1, this.editComponent = void 0, this.componentProperties = void 0, Ze(this, {
      notifications: x.shallow
    }), this.spotlightActive = fe.getSpotlightActivation() ?? !1;
  }
  setActive(t, n) {
    this.active = t, t && (this.activatedAtLeastOnce || (wt("activate"), this.idePluginState?.active && wt("plugin-active", {
      pluginVersion: this.idePluginState.version,
      ide: this.idePluginState.ide
    })), this.activatedAtLeastOnce = !0), this.activatedFrom = n ?? null;
  }
  setSpotlightActive(t) {
    this.spotlightActive = t;
  }
  setWelcomeActive(t) {
    this.welcomeActive = t;
  }
  setLoginCheckActive(t) {
    this.loginCheckActive = t;
  }
  setUserInfo(t) {
    this.userInfo = t;
  }
  startOperation(t) {
    if (this.operationInProgress)
      throw new Error(`An ${t} operation is already in progress`);
    if (this.operationWaitsHmrUpdate) {
      cl();
      return;
    }
    this.operationInProgress = t;
  }
  stopOperation(t) {
    if (this.operationInProgress) {
      if (this.operationInProgress !== t)
        return;
    } else return;
    this.operationInProgress = void 0;
  }
  setOperationWaitsHmrUpdate(t, n) {
    this.operationWaitsHmrUpdate = t, this.operationWaitsHmrUpdateTimeout = n;
  }
  clearOperationWaitsHmrUpdate() {
    this.operationWaitsHmrUpdate = void 0, this.operationWaitsHmrUpdateTimeout = void 0;
  }
  setIdePluginState(t) {
    this.idePluginState = t;
  }
  setJdkInfo(t) {
    this.jdkInfo = t;
  }
  toggleActive(t) {
    this.setActive(!this.active, this.active ? null : t ?? null);
  }
  reset() {
    this.active = !1, this.activatedAtLeastOnce = !1;
  }
  setNotifications(t) {
    this.notifications = t;
  }
  removeNotification(t) {
    t.animatingOut = !0, setTimeout(() => {
      this.reallyRemoveNotification(t);
    }, 180);
  }
  reallyRemoveNotification(t) {
    const n = this.notifications.indexOf(t);
    n > -1 && this.notifications.splice(n, 1);
  }
  setTooltip(t, n) {
    this.infoTooltip = {
      text: t,
      loader: n
    };
  }
  clearTooltip() {
    this.infoTooltip = null;
  }
  setSectionPanelDragging(t) {
    this.sectionPanelDragging = t;
  }
  setSpotlightDragging(t) {
    this.spotlightDragging = t;
  }
  setSectionPanelResizing(t) {
    this.sectionPanelResizing = t;
  }
  setDrawerResizing(t) {
    this.drawerResizing = t;
  }
  setFeatureFlags(t) {
    this.featureFlags = t;
  }
  setVaadinVersionState(t) {
    this.newVaadinVersionState = t;
  }
  setPointerEventsDisabledForScrolling(t) {
    this.pointerEventsDisabledForScrolling = t;
  }
  setEditComponent(t) {
    this.editComponent = t;
  }
  clearEditComponent() {
    this.editComponent = void 0;
  }
  setComponentProperties(t) {
    this.componentProperties = t;
  }
  clearComponentProperties() {
    this.componentProperties = void 0;
  }
}
const pl = (e, t, n) => t >= e.left && t <= e.right && n >= e.top && n <= e.bottom, vl = (e) => {
  const t = [];
  let n = gl(e);
  for (; n; )
    t.push(n), n = n.parentElement;
  return t;
}, hl = (e) => {
  if (e.length === 0)
    return new DOMRect();
  let t = Number.MAX_VALUE, n = Number.MAX_VALUE, r = Number.MIN_VALUE, i = Number.MIN_VALUE;
  const o = new DOMRect();
  return e.forEach((a) => {
    const s = a.getBoundingClientRect();
    s.x < t && (t = s.x), s.y < n && (n = s.y), s.right > r && (r = s.right), s.bottom > i && (i = s.bottom);
  }), o.x = t, o.y = n, o.width = r - t, o.height = i - n, o;
}, zn = (e, t) => {
  let n = e;
  for (; !(n instanceof HTMLElement && n.localName === `${Re}main`); ) {
    if (!n.isConnected)
      return null;
    if (n.parentNode)
      n = n.parentNode;
    else if (n.host)
      n = n.host;
    else
      return null;
    if (n instanceof HTMLElement && n.localName === t)
      return n;
  }
  return null;
};
function gl(e) {
  return e.parentElement ?? e.parentNode?.host;
}
function Un(e) {
  if (e.assignedSlot)
    return Un(e.assignedSlot);
  if (e.parentElement)
    return e.parentElement;
  if (e.parentNode instanceof ShadowRoot)
    return e.parentNode.host;
}
function Ge(e) {
  if (e instanceof Node) {
    const t = vl(e);
    return e instanceof HTMLElement && t.push(e), t.map((n) => n.localName).some((n) => n.startsWith(Re));
  }
  return !1;
}
function Pr(e) {
  return e instanceof Element;
}
function $r(e) {
  return e.startsWith("vaadin-") ? e.substring(7).split("-").map((r) => r.charAt(0).toUpperCase() + r.slice(1)).join(" ") : e;
}
function Dr(e) {
  if (!e)
    return;
  if (e.id)
    return `#${e.id}`;
  if (!e.children)
    return;
  const t = Array.from(e.children).find((r) => r.localName === "label");
  if (t)
    return t.outerText.trim();
  const n = Array.from(e.childNodes).find(
    (r) => r.nodeType === Node.TEXT_NODE && r.textContent && r.textContent.trim().length > 0
  );
  if (n && n.textContent)
    return n.textContent.trim();
}
function ml(e) {
  return typeof e.getBoundingClientRect == "function" ? e.getBoundingClientRect() : bl(e);
}
function bl(e) {
  const t = document.createRange();
  t.selectNode(e);
  const n = t.getBoundingClientRect();
  return t.detach(), n;
}
function _l() {
  let e = document.activeElement;
  for (; e?.shadowRoot && e.shadowRoot.activeElement; )
    e = e.shadowRoot.activeElement;
  return e;
}
function yl(e) {
  let t = Un(e);
  for (; t && t !== document.body; ) {
    const n = window.getComputedStyle(t), r = n.overflowY, i = n.overflowX, o = /(auto|scroll)/.test(r) && t.scrollHeight > t.clientHeight, a = /(auto|scroll)/.test(i) && t.scrollWidth > t.clientWidth;
    if (o || a)
      return t;
    t = Un(t);
  }
  return document.documentElement;
}
function wl(e, t) {
  return El(e, t) && Ol(t);
}
function El(e, t) {
  const n = yl(e), r = n.getBoundingClientRect();
  if (n === document.documentElement || n === document.body) {
    const i = window.innerWidth || document.documentElement.clientWidth, o = window.innerHeight || document.documentElement.clientHeight;
    return t.top < o && t.bottom > 0 && t.left < i && t.right > 0;
  }
  return t.bottom > r.top && t.top < r.bottom && t.right > r.left && t.left < r.right;
}
function Ol(e) {
  return e.bottom > 0 && e.right > 0 && e.top < window.innerHeight && e.left < window.innerWidth;
}
function $u(e) {
  return e instanceof HTMLElement;
}
function Du(e) {
  const t = so(e), n = hl(t);
  !t.every((i) => wl(i, n)) && t.length > 0 && t[0].scrollIntoView();
}
function so(e) {
  const t = e;
  if (!t)
    return [];
  const { element: n } = t;
  if (n) {
    const r = t.element;
    if (n.localName === "vaadin-popover" || n.localName === "vaadin-dialog") {
      const i = r._overlayElement.shadowRoot.querySelector('[part="overlay"]');
      if (i)
        return [i];
    }
    return [n];
  }
  return t.children.flatMap((r) => so(r));
}
function Al(e) {
  const { clientX: t, clientY: n } = e;
  return t === 0 && n === 0 || // Safari and Firefox returns the last position where mouse left the screen with adding some offset value, something like 356, -1.
  !pl(document.documentElement.getBoundingClientRect(), t, n);
}
function kr(e) {
  const t = ml(e);
  return t.width === 0 || t.height === 0;
}
var lo = /* @__PURE__ */ ((e) => (e["vaadin-combo-box"] = "vaadin-combo-box", e["vaadin-date-picker"] = "vaadin-date-picker", e["vaadin-dialog"] = "vaadin-dialog", e["vaadin-multi-select-combo-box"] = "vaadin-multi-select-combo-box", e["vaadin-select"] = "vaadin-select", e["vaadin-time-picker"] = "vaadin-time-picker", e["vaadin-popover"] = "vaadin-popover", e))(lo || {});
const ot = {
  "vaadin-combo-box": {
    hideOnActivation: !0,
    open: (e) => Tt(e),
    close: (e) => It(e)
  },
  "vaadin-select": {
    hideOnActivation: !0,
    open: (e) => {
      const t = e;
      uo(t, t._overlayElement), t.opened = !0;
    },
    close: (e) => {
      const t = e;
      fo(t, t._overlayElement), t.opened = !1;
    }
  },
  "vaadin-multi-select-combo-box": {
    hideOnActivation: !0,
    open: (e) => Tt(e.$.comboBox),
    close: (e) => {
      It(e.$.comboBox), e.removeAttribute("focused");
    }
  },
  "vaadin-date-picker": {
    hideOnActivation: !0,
    open: (e) => Tt(e),
    close: (e) => It(e)
  },
  "vaadin-time-picker": {
    hideOnActivation: !0,
    open: (e) => Tt(e.$.comboBox),
    close: (e) => {
      It(e.$.comboBox), e.removeAttribute("focused");
    }
  },
  "vaadin-dialog": {
    hideOnActivation: !1
  },
  "vaadin-popover": {
    hideOnActivation: !1
  }
}, co = (e) => {
  e.preventDefault(), e.stopImmediatePropagation();
}, Tt = (e) => {
  e.addEventListener("focusout", co, { capture: !0 }), uo(e), e.opened = !0;
}, It = (e) => {
  fo(e), e.removeAttribute("focused"), e.removeEventListener("focusout", co, { capture: !0 }), e.opened = !1;
}, uo = (e, t) => {
  const n = t ?? e.$.overlay;
  n.__oldModeless = n.modeless, n.modeless = !0;
}, fo = (e, t) => {
  const n = t ?? e.$.overlay;
  n.modeless = n.__oldModeless !== void 0 ? n.__oldModeless : n.modeless, delete n.__oldModeless;
};
class xl {
  constructor() {
    this.openedOverlayOwners = /* @__PURE__ */ new Set(), this.overlayCloseEventListener = (t) => {
      Ge(t.target?.owner) || (window.Vaadin.copilot._uiState.active || Ge(t.detail.sourceEvent.target)) && (t.preventDefault(), t.stopImmediatePropagation());
    };
  }
  /**
   * Modifies pointer-events property to auto if dialog overlay is present on body element. <br/>
   * Overriding closeOnOutsideClick method in order to keep overlay present while copilot is active
   * @private
   */
  onCopilotActivation() {
    const t = Array.from(document.body.children).find(
      (r) => r.localName.startsWith("vaadin") && r.localName.endsWith("-overlay")
    );
    if (!t)
      return;
    const n = this.getOwner(t);
    if (n) {
      const r = ot[n.localName];
      if (!r)
        return;
      r.hideOnActivation && r.close ? r.close(n) : document.body.style.getPropertyValue("pointer-events") === "none" && document.body.style.removeProperty("pointer-events");
    }
  }
  /**
   * Restores pointer-events state on deactivation. <br/>
   * Closes opened overlays while using copilot.
   * @private
   */
  onCopilotDeactivation() {
    this.openedOverlayOwners.forEach((n) => {
      const r = ot[n.localName];
      r && r.close && r.close(n);
    }), document.body.querySelector("vaadin-dialog-overlay") && document.body.style.setProperty("pointer-events", "none");
  }
  getOwner(t) {
    const n = t;
    return n.owner ?? n.__dataHost;
  }
  addOverlayOutsideClickEvent() {
    document.documentElement.addEventListener("vaadin-overlay-outside-click", this.overlayCloseEventListener, {
      capture: !0
    }), document.documentElement.addEventListener("vaadin-overlay-escape-press", this.overlayCloseEventListener, {
      capture: !0
    });
  }
  removeOverlayOutsideClickEvent() {
    document.documentElement.removeEventListener("vaadin-overlay-outside-click", this.overlayCloseEventListener), document.documentElement.removeEventListener("vaadin-overlay-escape-press", this.overlayCloseEventListener);
  }
  toggle(t) {
    const n = ot[t.localName];
    this.isOverlayActive(t) ? (n.close(t), this.openedOverlayOwners.delete(t)) : (n.open(t), this.openedOverlayOwners.add(t));
  }
  isOverlayActive(t) {
    const n = ot[t.localName];
    return n.active ? n.active(t) : t.hasAttribute("opened");
  }
  overlayStatus(t) {
    if (!t)
      return { visible: !1 };
    const n = t.localName;
    let r = Object.keys(lo).includes(n);
    if (!r)
      return { visible: !1 };
    const i = ot[t.localName];
    if (!i.open || !i.close)
      return { visible: !1 };
    i.hasOverlay && (r = i.hasOverlay(t));
    const o = this.isOverlayActive(t);
    return { visible: r, active: o };
  }
}
class po {
  constructor() {
    this.promise = new Promise((t) => {
      this.resolveInit = t;
    });
  }
  done(t) {
    this.resolveInit(t);
  }
}
class Sl {
  constructor() {
    this.dismissedNotifications = [], this.termsSummaryDismissed = !1, this.activationButtonPosition = null, this.paletteState = null, this.activationShortcut = !0, this.activationAnimation = !0, this.recentSwitchedUsernames = [], this.newVersionPreReleasesVisible = !1, this.aiUsageAllowed = "ask", this.sendErrorReportsAllowed = !0, this.feedbackDisplayedAtLeastOnce = !1, Ze(this), this.initializer = new po(), this.initializer.promise.then(() => {
      Qn(
        () => JSON.stringify(this),
        () => {
          te("copilot-set-machine-configuration", { conf: JSON.stringify(Tr(this)) });
        }
      );
    }), window.Vaadin.copilot.eventbus.on("copilot-machine-configuration", (t) => {
      const n = t.detail.conf;
      Object.assign(this, Tr(n)), this.initializer.done(!0), t.preventDefault();
    }), this.loadData();
  }
  loadData() {
    te("copilot-get-machine-configuration", {});
  }
  addDismissedNotification(t) {
    this.dismissedNotifications = [...this.dismissedNotifications, t];
  }
  getDismissedNotifications() {
    return this.dismissedNotifications;
  }
  clearDismissedNotifications() {
    this.dismissedNotifications = [];
  }
  setTermsSummaryDismissed(t) {
    this.termsSummaryDismissed = t;
  }
  isTermsSummaryDismissed() {
    return this.termsSummaryDismissed;
  }
  getActivationButtonPosition() {
    return this.activationButtonPosition;
  }
  setActivationButtonPosition(t) {
    this.activationButtonPosition = t;
  }
  getPaletteState() {
    return this.paletteState;
  }
  setPaletteState(t) {
    this.paletteState = t;
  }
  isActivationShortcut() {
    return this.activationShortcut;
  }
  setActivationShortcut(t) {
    this.activationShortcut = t;
  }
  isActivationAnimation() {
    return this.activationAnimation;
  }
  setActivationAnimation(t) {
    this.activationAnimation = t;
  }
  getRecentSwitchedUsernames() {
    return this.recentSwitchedUsernames;
  }
  setRecentSwitchedUsernames(t) {
    this.recentSwitchedUsernames = t;
  }
  addRecentSwitchedUsername(t) {
    this.setRecentSwitchedUsernames(ll([t, ...this.recentSwitchedUsernames]));
  }
  removeRecentSwitchedUsername(t) {
    this.setRecentSwitchedUsernames(this.recentSwitchedUsernames.filter((n) => n !== t));
  }
  getNewVersionPreReleasesVisible() {
    return this.newVersionPreReleasesVisible;
  }
  setNewVersionPreReleasesVisible(t) {
    this.newVersionPreReleasesVisible = t;
  }
  setSendErrorReportsAllowed(t) {
    this.sendErrorReportsAllowed = t;
  }
  isSendErrorReportsAllowed() {
    return this.sendErrorReportsAllowed;
  }
  setAIUsageAllowed(t) {
    this.aiUsageAllowed = t;
  }
  isAIUsageAllowed() {
    return this.aiUsageAllowed;
  }
  setFeedbackDisplayedAtLeastOnce(t) {
    this.feedbackDisplayedAtLeastOnce = t;
  }
  isFeedbackDisplayedAtLeastOnce() {
    return this.feedbackDisplayedAtLeastOnce;
  }
}
function Tr(e) {
  const t = { ...e };
  return delete t.initializer, t;
}
class Nl {
  constructor() {
    this._previewActivated = !1, this._remainingTimeInMillis = -1, this._active = !1, this._configurationLoaded = !1, Ze(this);
  }
  setConfiguration(t) {
    this._previewActivated = t.previewActivated, t.previewActivated ? this._remainingTimeInMillis = t.remainingTimeInMillis : this._remainingTimeInMillis = -1, this._active = t.active, this._configurationLoaded = !0;
  }
  get previewActivated() {
    return this._previewActivated;
  }
  get remainingTimeInMillis() {
    return this._remainingTimeInMillis;
  }
  get active() {
    return this._active;
  }
  get configurationLoaded() {
    return this._configurationLoaded;
  }
  get expired() {
    return this.previewActivated && !this.active;
  }
  reset() {
    this._previewActivated = !1, this._active = !1, this._configurationLoaded = !1, this._remainingTimeInMillis = -1;
  }
  loadPreviewConfiguration() {
    vn(`${Re}get-preview`, {}, (t) => {
      const n = t.data;
      this.setConfiguration(n);
    }).catch((t) => {
      Promise.resolve().then(() => Ac).then((n) => {
        n.handleCopilotError("Load preview configuration failed", t);
      });
    });
  }
}
class Cl {
  constructor() {
    this._panels = [], this._attentionRequiredPanelTag = null, this._floatingPanelsZIndexOrder = [], this.renderedPanels = /* @__PURE__ */ new Set(), this.customTags = /* @__PURE__ */ new Map(), Ze(this), this.restorePositions();
  }
  shouldRender(t) {
    return this.renderedPanels.has(t);
  }
  restorePositions() {
    const t = fe.getPanelConfigurations();
    t && (this._panels = this._panels.map((n) => {
      const r = t.find((i) => i.tag === n.tag);
      return r && (n = Object.assign(n, { ...r })), n;
    }));
  }
  /**
   * Brings a given floating panel to the front.
   *
   * @param panelTag the tag name of the panel
   */
  bringToFront(t) {
    this._floatingPanelsZIndexOrder = this._floatingPanelsZIndexOrder.filter((n) => n !== t), this.getPanelByTag(t)?.floating && this._floatingPanelsZIndexOrder.push(t);
  }
  /**
   * Returns the focused z-index of floating panel as following order
   * <ul>
   *     <li>Returns 50 for last(focused) element </li>
   *     <li>Returns the index of element in list(starting from 0) </li>
   *     <li>Returns 0 if panel is not in the list</li>
   * </ul>
   * @param panelTag
   */
  getFloatingPanelZIndex(t) {
    const n = this._floatingPanelsZIndexOrder.findIndex((r) => r === t);
    return n === this._floatingPanelsZIndexOrder.length - 1 ? 50 : n === -1 ? 0 : n;
  }
  get floatingPanelsZIndexOrder() {
    return this._floatingPanelsZIndexOrder;
  }
  get attentionRequiredPanelTag() {
    return this._attentionRequiredPanelTag;
  }
  set attentionRequiredPanelTag(t) {
    this._attentionRequiredPanelTag = t;
  }
  getAttentionRequiredPanelConfiguration() {
    return this._panels.find((t) => t.tag === this._attentionRequiredPanelTag);
  }
  clearAttention() {
    this._attentionRequiredPanelTag = null;
  }
  get panels() {
    return this._panels;
  }
  addPanel(t) {
    if (this.getPanelByTag(t.tag))
      return;
    this._panels.push(t), this.restorePositions();
    const n = this.getPanelByTag(t.tag);
    if (n)
      (n.eager || n.expanded) && this.renderedPanels.add(t.tag);
    else throw new Error(`Panel configuration not found for tag ${t.tag}`);
  }
  getPanelByTag(t) {
    return this._panels.find((n) => n.tag === t);
  }
  updatePanel(t, n) {
    const r = [...this._panels], i = r.find((o) => o.tag === t);
    if (i) {
      for (const o in n)
        i[o] = n[o];
      i.expanded && this.renderedPanels.add(i.tag), n.floating === !1 && (this._floatingPanelsZIndexOrder = this._floatingPanelsZIndexOrder.filter((o) => o !== t)), this._panels = r, fe.savePanelConfigurations(this._panels);
    }
  }
  updateOrders(t) {
    const n = [...this._panels];
    n.forEach((r) => {
      const i = t.find((o) => o.tag === r.tag);
      i && (r.panelOrder = i.order);
    }), this._panels = n, fe.savePanelConfigurations(n);
  }
  removePanel(t) {
    const n = this._panels.findIndex((r) => r.tag === t);
    n < 0 || (this._panels.splice(n, 1), fe.savePanelConfigurations(this._panels));
  }
  setCustomPanelHeader(t, n) {
    this.customTags.set(t.tag, n);
  }
  getPanelHeader(t) {
    return this.customTags.get(t.tag) ?? t.header;
  }
  clearCustomPanelHeader(t) {
    this.customTags.delete(t.tag);
  }
}
class Pl {
  constructor() {
    this.supportsHilla = !0, this.springSecurityEnabled = !1, this.springJpaDataEnabled = !1, this.springApplication = !1, this.urlPrefix = "", Ze(this);
  }
  setSupportsHilla(t) {
    this.supportsHilla = t;
  }
  setSpringSecurityEnabled(t) {
    this.springSecurityEnabled = t;
  }
  setSpringJpaDataEnabled(t) {
    this.springJpaDataEnabled = t;
  }
  setSpringApplication(t) {
    this.springApplication = t;
  }
  setUrlPrefix(t) {
    this.urlPrefix = t;
  }
}
class $l {
  constructor() {
    this.palette = { components: [] }, Ze(this), this.initializer = new po(), this.initializer.promise.then(() => {
      Qn(
        () => JSON.stringify(this),
        () => {
          te("copilot-set-project-state-configuration", { conf: JSON.stringify(Ir(this)) });
        }
      );
    }), window.Vaadin.copilot.eventbus.on("copilot-project-state-configuration", (t) => {
      const n = t.detail.conf;
      Object.assign(this, Ir(n)), this.initializer.done(!0), t.preventDefault();
    }), this.loadData();
  }
  loadData() {
    te("copilot-get-project-state-configuration", {});
  }
  addPaletteCustomComponent(t) {
    return (this.palette?.components ?? []).find((i) => An(i, t)) ? !1 : (this.palette || (this.palette = { components: [] }), this.palette = JSON.parse(JSON.stringify(this.palette)), this.palette.components.push(t), !0);
  }
  removePaletteCustomComponent(t) {
    if (this.palette) {
      const n = this.palette.components.findIndex(
        (r) => An(r, t)
      );
      n > -1 && this.palette.components.splice(n, 1);
    }
  }
  updatePaletteCustomComponent(t, n) {
    if (!this.palette || !this.palette.components)
      return;
    const r = [...this.palette.components], i = r.findIndex((o) => An(o, t));
    i !== -1 && (r[i] = { ...t, ...n }), this.palette.components = r;
  }
  paletteCustomComponentExist(t, n) {
    return !this.palette || !this.palette.components ? !1 : t ? this.palette.components.findIndex(
      (r) => r.java && !r.react && r.javaClassName === t
    ) !== -1 : n ? this.palette.components.findIndex((r) => !r.java && r.react && r.template === n) !== -1 : !1;
  }
  get paletteComponents() {
    return this.palette?.components || [];
  }
}
function Ir(e) {
  const t = { ...e };
  return delete t.initializer, t;
}
function An(e, t) {
  return e.java ? t.java ? e.javaClassName === t.javaClassName : !1 : e.react && t.react ? e.template === t.template : !1;
}
window.Vaadin ??= {};
window.Vaadin.copilot ??= {};
window.Vaadin.copilot.plugins = [];
window.Vaadin.copilot._uiState = new fl();
window.Vaadin.copilot.eventbus = new Uo();
window.Vaadin.copilot.overlayManager = new xl();
window.Vaadin.copilot._machineState = new Sl();
window.Vaadin.copilot._storedProjectState = new $l();
window.Vaadin.copilot._previewState = new Nl();
window.Vaadin.copilot._sectionPanelUiState = new Cl();
window.Vaadin.copilot._earlyProjectState = new Pl();
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const Dl = (e) => (t, n) => {
  n !== void 0 ? n.addInitializer(() => {
    customElements.define(e, t);
  }) : customElements.define(e, t);
};
/**
 * @license
 * Copyright 2019 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const Lt = globalThis, rr = Lt.ShadowRoot && (Lt.ShadyCSS === void 0 || Lt.ShadyCSS.nativeShadow) && "adoptedStyleSheets" in Document.prototype && "replace" in CSSStyleSheet.prototype, ir = Symbol(), Vr = /* @__PURE__ */ new WeakMap();
let vo = class {
  constructor(t, n, r) {
    if (this._$cssResult$ = !0, r !== ir) throw Error("CSSResult is not constructable. Use `unsafeCSS` or `css` instead.");
    this.cssText = t, this.t = n;
  }
  get styleSheet() {
    let t = this.o;
    const n = this.t;
    if (rr && t === void 0) {
      const r = n !== void 0 && n.length === 1;
      r && (t = Vr.get(n)), t === void 0 && ((this.o = t = new CSSStyleSheet()).replaceSync(this.cssText), r && Vr.set(n, t));
    }
    return t;
  }
  toString() {
    return this.cssText;
  }
};
const R = (e) => new vo(typeof e == "string" ? e : e + "", void 0, ir), kl = (e, ...t) => {
  const n = e.length === 1 ? e[0] : t.reduce((r, i, o) => r + ((a) => {
    if (a._$cssResult$ === !0) return a.cssText;
    if (typeof a == "number") return a;
    throw Error("Value passed to 'css' function must be a 'css' function result: " + a + ". Use 'unsafeCSS' to pass non-literal values, but take care to ensure page security.");
  })(i) + e[o + 1], e[0]);
  return new vo(n, e, ir);
}, Tl = (e, t) => {
  if (rr) e.adoptedStyleSheets = t.map((n) => n instanceof CSSStyleSheet ? n : n.styleSheet);
  else for (const n of t) {
    const r = document.createElement("style"), i = Lt.litNonce;
    i !== void 0 && r.setAttribute("nonce", i), r.textContent = n.cssText, e.appendChild(r);
  }
}, Rr = rr ? (e) => e : (e) => e instanceof CSSStyleSheet ? ((t) => {
  let n = "";
  for (const r of t.cssRules) n += r.cssText;
  return R(n);
})(e) : e;
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const { is: Il, defineProperty: Vl, getOwnPropertyDescriptor: Rl, getOwnPropertyNames: jl, getOwnPropertySymbols: Ml, getPrototypeOf: Ll } = Object, gn = globalThis, jr = gn.trustedTypes, zl = jr ? jr.emptyScript : "", Ul = gn.reactiveElementPolyfillSupport, ht = (e, t) => e, Bn = { toAttribute(e, t) {
  switch (t) {
    case Boolean:
      e = e ? zl : null;
      break;
    case Object:
    case Array:
      e = e == null ? e : JSON.stringify(e);
  }
  return e;
}, fromAttribute(e, t) {
  let n = e;
  switch (t) {
    case Boolean:
      n = e !== null;
      break;
    case Number:
      n = e === null ? null : Number(e);
      break;
    case Object:
    case Array:
      try {
        n = JSON.parse(e);
      } catch {
        n = null;
      }
  }
  return n;
} }, ho = (e, t) => !Il(e, t), Mr = { attribute: !0, type: String, converter: Bn, reflect: !1, useDefault: !1, hasChanged: ho };
Symbol.metadata ??= Symbol("metadata"), gn.litPropertyMetadata ??= /* @__PURE__ */ new WeakMap();
let Le = class extends HTMLElement {
  static addInitializer(t) {
    this._$Ei(), (this.l ??= []).push(t);
  }
  static get observedAttributes() {
    return this.finalize(), this._$Eh && [...this._$Eh.keys()];
  }
  static createProperty(t, n = Mr) {
    if (n.state && (n.attribute = !1), this._$Ei(), this.prototype.hasOwnProperty(t) && ((n = Object.create(n)).wrapped = !0), this.elementProperties.set(t, n), !n.noAccessor) {
      const r = Symbol(), i = this.getPropertyDescriptor(t, r, n);
      i !== void 0 && Vl(this.prototype, t, i);
    }
  }
  static getPropertyDescriptor(t, n, r) {
    const { get: i, set: o } = Rl(this.prototype, t) ?? { get() {
      return this[n];
    }, set(a) {
      this[n] = a;
    } };
    return { get: i, set(a) {
      const s = i?.call(this);
      o?.call(this, a), this.requestUpdate(t, s, r);
    }, configurable: !0, enumerable: !0 };
  }
  static getPropertyOptions(t) {
    return this.elementProperties.get(t) ?? Mr;
  }
  static _$Ei() {
    if (this.hasOwnProperty(ht("elementProperties"))) return;
    const t = Ll(this);
    t.finalize(), t.l !== void 0 && (this.l = [...t.l]), this.elementProperties = new Map(t.elementProperties);
  }
  static finalize() {
    if (this.hasOwnProperty(ht("finalized"))) return;
    if (this.finalized = !0, this._$Ei(), this.hasOwnProperty(ht("properties"))) {
      const n = this.properties, r = [...jl(n), ...Ml(n)];
      for (const i of r) this.createProperty(i, n[i]);
    }
    const t = this[Symbol.metadata];
    if (t !== null) {
      const n = litPropertyMetadata.get(t);
      if (n !== void 0) for (const [r, i] of n) this.elementProperties.set(r, i);
    }
    this._$Eh = /* @__PURE__ */ new Map();
    for (const [n, r] of this.elementProperties) {
      const i = this._$Eu(n, r);
      i !== void 0 && this._$Eh.set(i, n);
    }
    this.elementStyles = this.finalizeStyles(this.styles);
  }
  static finalizeStyles(t) {
    const n = [];
    if (Array.isArray(t)) {
      const r = new Set(t.flat(1 / 0).reverse());
      for (const i of r) n.unshift(Rr(i));
    } else t !== void 0 && n.push(Rr(t));
    return n;
  }
  static _$Eu(t, n) {
    const r = n.attribute;
    return r === !1 ? void 0 : typeof r == "string" ? r : typeof t == "string" ? t.toLowerCase() : void 0;
  }
  constructor() {
    super(), this._$Ep = void 0, this.isUpdatePending = !1, this.hasUpdated = !1, this._$Em = null, this._$Ev();
  }
  _$Ev() {
    this._$ES = new Promise((t) => this.enableUpdating = t), this._$AL = /* @__PURE__ */ new Map(), this._$E_(), this.requestUpdate(), this.constructor.l?.forEach((t) => t(this));
  }
  addController(t) {
    (this._$EO ??= /* @__PURE__ */ new Set()).add(t), this.renderRoot !== void 0 && this.isConnected && t.hostConnected?.();
  }
  removeController(t) {
    this._$EO?.delete(t);
  }
  _$E_() {
    const t = /* @__PURE__ */ new Map(), n = this.constructor.elementProperties;
    for (const r of n.keys()) this.hasOwnProperty(r) && (t.set(r, this[r]), delete this[r]);
    t.size > 0 && (this._$Ep = t);
  }
  createRenderRoot() {
    const t = this.shadowRoot ?? this.attachShadow(this.constructor.shadowRootOptions);
    return Tl(t, this.constructor.elementStyles), t;
  }
  connectedCallback() {
    this.renderRoot ??= this.createRenderRoot(), this.enableUpdating(!0), this._$EO?.forEach((t) => t.hostConnected?.());
  }
  enableUpdating(t) {
  }
  disconnectedCallback() {
    this._$EO?.forEach((t) => t.hostDisconnected?.());
  }
  attributeChangedCallback(t, n, r) {
    this._$AK(t, r);
  }
  _$ET(t, n) {
    const r = this.constructor.elementProperties.get(t), i = this.constructor._$Eu(t, r);
    if (i !== void 0 && r.reflect === !0) {
      const o = (r.converter?.toAttribute !== void 0 ? r.converter : Bn).toAttribute(n, r.type);
      this._$Em = t, o == null ? this.removeAttribute(i) : this.setAttribute(i, o), this._$Em = null;
    }
  }
  _$AK(t, n) {
    const r = this.constructor, i = r._$Eh.get(t);
    if (i !== void 0 && this._$Em !== i) {
      const o = r.getPropertyOptions(i), a = typeof o.converter == "function" ? { fromAttribute: o.converter } : o.converter?.fromAttribute !== void 0 ? o.converter : Bn;
      this._$Em = i, this[i] = a.fromAttribute(n, o.type) ?? this._$Ej?.get(i) ?? null, this._$Em = null;
    }
  }
  requestUpdate(t, n, r) {
    if (t !== void 0) {
      const i = this.constructor, o = this[t];
      if (r ??= i.getPropertyOptions(t), !((r.hasChanged ?? ho)(o, n) || r.useDefault && r.reflect && o === this._$Ej?.get(t) && !this.hasAttribute(i._$Eu(t, r)))) return;
      this.C(t, n, r);
    }
    this.isUpdatePending === !1 && (this._$ES = this._$EP());
  }
  C(t, n, { useDefault: r, reflect: i, wrapped: o }, a) {
    r && !(this._$Ej ??= /* @__PURE__ */ new Map()).has(t) && (this._$Ej.set(t, a ?? n ?? this[t]), o !== !0 || a !== void 0) || (this._$AL.has(t) || (this.hasUpdated || r || (n = void 0), this._$AL.set(t, n)), i === !0 && this._$Em !== t && (this._$Eq ??= /* @__PURE__ */ new Set()).add(t));
  }
  async _$EP() {
    this.isUpdatePending = !0;
    try {
      await this._$ES;
    } catch (n) {
      Promise.reject(n);
    }
    const t = this.scheduleUpdate();
    return t != null && await t, !this.isUpdatePending;
  }
  scheduleUpdate() {
    return this.performUpdate();
  }
  performUpdate() {
    if (!this.isUpdatePending) return;
    if (!this.hasUpdated) {
      if (this.renderRoot ??= this.createRenderRoot(), this._$Ep) {
        for (const [i, o] of this._$Ep) this[i] = o;
        this._$Ep = void 0;
      }
      const r = this.constructor.elementProperties;
      if (r.size > 0) for (const [i, o] of r) {
        const { wrapped: a } = o, s = this[i];
        a !== !0 || this._$AL.has(i) || s === void 0 || this.C(i, void 0, o, s);
      }
    }
    let t = !1;
    const n = this._$AL;
    try {
      t = this.shouldUpdate(n), t ? (this.willUpdate(n), this._$EO?.forEach((r) => r.hostUpdate?.()), this.update(n)) : this._$EM();
    } catch (r) {
      throw t = !1, this._$EM(), r;
    }
    t && this._$AE(n);
  }
  willUpdate(t) {
  }
  _$AE(t) {
    this._$EO?.forEach((n) => n.hostUpdated?.()), this.hasUpdated || (this.hasUpdated = !0, this.firstUpdated(t)), this.updated(t);
  }
  _$EM() {
    this._$AL = /* @__PURE__ */ new Map(), this.isUpdatePending = !1;
  }
  get updateComplete() {
    return this.getUpdateComplete();
  }
  getUpdateComplete() {
    return this._$ES;
  }
  shouldUpdate(t) {
    return !0;
  }
  update(t) {
    this._$Eq &&= this._$Eq.forEach((n) => this._$ET(n, this[n])), this._$EM();
  }
  updated(t) {
  }
  firstUpdated(t) {
  }
};
Le.elementStyles = [], Le.shadowRootOptions = { mode: "open" }, Le[ht("elementProperties")] = /* @__PURE__ */ new Map(), Le[ht("finalized")] = /* @__PURE__ */ new Map(), Ul?.({ ReactiveElement: Le }), (gn.reactiveElementVersions ??= []).push("2.1.0");
const Me = Symbol("LitMobxRenderReaction"), Lr = Symbol("LitMobxRequestUpdate");
function Bl(e, t) {
  var n, r;
  return r = class extends e {
    constructor() {
      super(...arguments), this[n] = () => {
        this.requestUpdate();
      };
    }
    connectedCallback() {
      super.connectedCallback();
      const o = this.constructor.name || this.nodeName;
      this[Me] = new t(`${o}.update()`, this[Lr]), this.hasUpdated && this.requestUpdate();
    }
    disconnectedCallback() {
      super.disconnectedCallback(), this[Me] && (this[Me].dispose(), this[Me] = void 0);
    }
    update(o) {
      this[Me] ? this[Me].track(super.update.bind(this, o)) : super.update(o);
    }
  }, n = Lr, r;
}
function Fl(e) {
  return Bl(e, ee);
}
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const or = globalThis, tn = or.trustedTypes, zr = tn ? tn.createPolicy("lit-html", { createHTML: (e) => e }) : void 0, go = "$lit$", ue = `lit$${Math.random().toFixed(9).slice(2)}$`, mo = "?" + ue, Hl = `<${mo}>`, De = document, Et = () => De.createComment(""), Ot = (e) => e === null || typeof e != "object" && typeof e != "function", ar = Array.isArray, ql = (e) => ar(e) || typeof e?.[Symbol.iterator] == "function", xn = `[ 	
\f\r]`, at = /<(?:(!--|\/[^a-zA-Z])|(\/?[a-zA-Z][^>\s]*)|(\/?$))/g, Ur = /-->/g, Br = />/g, ye = RegExp(`>|${xn}(?:([^\\s"'>=/]+)(${xn}*=${xn}*(?:[^ 	
\f\r"'\`<>=]|("|')|))|$)`, "g"), Fr = /'/g, Hr = /"/g, bo = /^(?:script|style|textarea|title)$/i, _o = (e) => (t, ...n) => ({ _$litType$: e, strings: t, values: n }), pe = _o(1), Vu = _o(2), he = Symbol.for("lit-noChange"), O = Symbol.for("lit-nothing"), qr = /* @__PURE__ */ new WeakMap(), Ae = De.createTreeWalker(De, 129);
function yo(e, t) {
  if (!ar(e) || !e.hasOwnProperty("raw")) throw Error("invalid template strings array");
  return zr !== void 0 ? zr.createHTML(t) : t;
}
const Kl = (e, t) => {
  const n = e.length - 1, r = [];
  let i, o = t === 2 ? "<svg>" : t === 3 ? "<math>" : "", a = at;
  for (let s = 0; s < n; s++) {
    const l = e[s];
    let c, u, d = -1, v = 0;
    for (; v < l.length && (a.lastIndex = v, u = a.exec(l), u !== null); ) v = a.lastIndex, a === at ? u[1] === "!--" ? a = Ur : u[1] !== void 0 ? a = Br : u[2] !== void 0 ? (bo.test(u[2]) && (i = RegExp("</" + u[2], "g")), a = ye) : u[3] !== void 0 && (a = ye) : a === ye ? u[0] === ">" ? (a = i ?? at, d = -1) : u[1] === void 0 ? d = -2 : (d = a.lastIndex - u[2].length, c = u[1], a = u[3] === void 0 ? ye : u[3] === '"' ? Hr : Fr) : a === Hr || a === Fr ? a = ye : a === Ur || a === Br ? a = at : (a = ye, i = void 0);
    const h = a === ye && e[s + 1].startsWith("/>") ? " " : "";
    o += a === at ? l + Hl : d >= 0 ? (r.push(c), l.slice(0, d) + go + l.slice(d) + ue + h) : l + ue + (d === -2 ? s : h);
  }
  return [yo(e, o + (e[n] || "<?>") + (t === 2 ? "</svg>" : t === 3 ? "</math>" : "")), r];
};
class At {
  constructor({ strings: t, _$litType$: n }, r) {
    let i;
    this.parts = [];
    let o = 0, a = 0;
    const s = t.length - 1, l = this.parts, [c, u] = Kl(t, n);
    if (this.el = At.createElement(c, r), Ae.currentNode = this.el.content, n === 2 || n === 3) {
      const d = this.el.content.firstChild;
      d.replaceWith(...d.childNodes);
    }
    for (; (i = Ae.nextNode()) !== null && l.length < s; ) {
      if (i.nodeType === 1) {
        if (i.hasAttributes()) for (const d of i.getAttributeNames()) if (d.endsWith(go)) {
          const v = u[a++], h = i.getAttribute(d).split(ue), b = /([.?@])?(.*)/.exec(v);
          l.push({ type: 1, index: o, name: b[2], strings: h, ctor: b[1] === "." ? Gl : b[1] === "?" ? Yl : b[1] === "@" ? Jl : mn }), i.removeAttribute(d);
        } else d.startsWith(ue) && (l.push({ type: 6, index: o }), i.removeAttribute(d));
        if (bo.test(i.tagName)) {
          const d = i.textContent.split(ue), v = d.length - 1;
          if (v > 0) {
            i.textContent = tn ? tn.emptyScript : "";
            for (let h = 0; h < v; h++) i.append(d[h], Et()), Ae.nextNode(), l.push({ type: 2, index: ++o });
            i.append(d[v], Et());
          }
        }
      } else if (i.nodeType === 8) if (i.data === mo) l.push({ type: 2, index: o });
      else {
        let d = -1;
        for (; (d = i.data.indexOf(ue, d + 1)) !== -1; ) l.push({ type: 7, index: o }), d += ue.length - 1;
      }
      o++;
    }
  }
  static createElement(t, n) {
    const r = De.createElement("template");
    return r.innerHTML = t, r;
  }
}
function Ye(e, t, n = e, r) {
  if (t === he) return t;
  let i = r !== void 0 ? n._$Co?.[r] : n._$Cl;
  const o = Ot(t) ? void 0 : t._$litDirective$;
  return i?.constructor !== o && (i?._$AO?.(!1), o === void 0 ? i = void 0 : (i = new o(e), i._$AT(e, n, r)), r !== void 0 ? (n._$Co ??= [])[r] = i : n._$Cl = i), i !== void 0 && (t = Ye(e, i._$AS(e, t.values), i, r)), t;
}
let Wl = class {
  constructor(t, n) {
    this._$AV = [], this._$AN = void 0, this._$AD = t, this._$AM = n;
  }
  get parentNode() {
    return this._$AM.parentNode;
  }
  get _$AU() {
    return this._$AM._$AU;
  }
  u(t) {
    const { el: { content: n }, parts: r } = this._$AD, i = (t?.creationScope ?? De).importNode(n, !0);
    Ae.currentNode = i;
    let o = Ae.nextNode(), a = 0, s = 0, l = r[0];
    for (; l !== void 0; ) {
      if (a === l.index) {
        let c;
        l.type === 2 ? c = new tt(o, o.nextSibling, this, t) : l.type === 1 ? c = new l.ctor(o, l.name, l.strings, this, t) : l.type === 6 && (c = new Xl(o, this, t)), this._$AV.push(c), l = r[++s];
      }
      a !== l?.index && (o = Ae.nextNode(), a++);
    }
    return Ae.currentNode = De, i;
  }
  p(t) {
    let n = 0;
    for (const r of this._$AV) r !== void 0 && (r.strings !== void 0 ? (r._$AI(t, r, n), n += r.strings.length - 2) : r._$AI(t[n])), n++;
  }
};
class tt {
  get _$AU() {
    return this._$AM?._$AU ?? this._$Cv;
  }
  constructor(t, n, r, i) {
    this.type = 2, this._$AH = O, this._$AN = void 0, this._$AA = t, this._$AB = n, this._$AM = r, this.options = i, this._$Cv = i?.isConnected ?? !0;
  }
  get parentNode() {
    let t = this._$AA.parentNode;
    const n = this._$AM;
    return n !== void 0 && t?.nodeType === 11 && (t = n.parentNode), t;
  }
  get startNode() {
    return this._$AA;
  }
  get endNode() {
    return this._$AB;
  }
  _$AI(t, n = this) {
    t = Ye(this, t, n), Ot(t) ? t === O || t == null || t === "" ? (this._$AH !== O && this._$AR(), this._$AH = O) : t !== this._$AH && t !== he && this._(t) : t._$litType$ !== void 0 ? this.$(t) : t.nodeType !== void 0 ? this.T(t) : ql(t) ? this.k(t) : this._(t);
  }
  O(t) {
    return this._$AA.parentNode.insertBefore(t, this._$AB);
  }
  T(t) {
    this._$AH !== t && (this._$AR(), this._$AH = this.O(t));
  }
  _(t) {
    this._$AH !== O && Ot(this._$AH) ? this._$AA.nextSibling.data = t : this.T(De.createTextNode(t)), this._$AH = t;
  }
  $(t) {
    const { values: n, _$litType$: r } = t, i = typeof r == "number" ? this._$AC(t) : (r.el === void 0 && (r.el = At.createElement(yo(r.h, r.h[0]), this.options)), r);
    if (this._$AH?._$AD === i) this._$AH.p(n);
    else {
      const o = new Wl(i, this), a = o.u(this.options);
      o.p(n), this.T(a), this._$AH = o;
    }
  }
  _$AC(t) {
    let n = qr.get(t.strings);
    return n === void 0 && qr.set(t.strings, n = new At(t)), n;
  }
  k(t) {
    ar(this._$AH) || (this._$AH = [], this._$AR());
    const n = this._$AH;
    let r, i = 0;
    for (const o of t) i === n.length ? n.push(r = new tt(this.O(Et()), this.O(Et()), this, this.options)) : r = n[i], r._$AI(o), i++;
    i < n.length && (this._$AR(r && r._$AB.nextSibling, i), n.length = i);
  }
  _$AR(t = this._$AA.nextSibling, n) {
    for (this._$AP?.(!1, !0, n); t && t !== this._$AB; ) {
      const r = t.nextSibling;
      t.remove(), t = r;
    }
  }
  setConnected(t) {
    this._$AM === void 0 && (this._$Cv = t, this._$AP?.(t));
  }
}
class mn {
  get tagName() {
    return this.element.tagName;
  }
  get _$AU() {
    return this._$AM._$AU;
  }
  constructor(t, n, r, i, o) {
    this.type = 1, this._$AH = O, this._$AN = void 0, this.element = t, this.name = n, this._$AM = i, this.options = o, r.length > 2 || r[0] !== "" || r[1] !== "" ? (this._$AH = Array(r.length - 1).fill(new String()), this.strings = r) : this._$AH = O;
  }
  _$AI(t, n = this, r, i) {
    const o = this.strings;
    let a = !1;
    if (o === void 0) t = Ye(this, t, n, 0), a = !Ot(t) || t !== this._$AH && t !== he, a && (this._$AH = t);
    else {
      const s = t;
      let l, c;
      for (t = o[0], l = 0; l < o.length - 1; l++) c = Ye(this, s[r + l], n, l), c === he && (c = this._$AH[l]), a ||= !Ot(c) || c !== this._$AH[l], c === O ? t = O : t !== O && (t += (c ?? "") + o[l + 1]), this._$AH[l] = c;
    }
    a && !i && this.j(t);
  }
  j(t) {
    t === O ? this.element.removeAttribute(this.name) : this.element.setAttribute(this.name, t ?? "");
  }
}
class Gl extends mn {
  constructor() {
    super(...arguments), this.type = 3;
  }
  j(t) {
    this.element[this.name] = t === O ? void 0 : t;
  }
}
class Yl extends mn {
  constructor() {
    super(...arguments), this.type = 4;
  }
  j(t) {
    this.element.toggleAttribute(this.name, !!t && t !== O);
  }
}
class Jl extends mn {
  constructor(t, n, r, i, o) {
    super(t, n, r, i, o), this.type = 5;
  }
  _$AI(t, n = this) {
    if ((t = Ye(this, t, n, 0) ?? O) === he) return;
    const r = this._$AH, i = t === O && r !== O || t.capture !== r.capture || t.once !== r.once || t.passive !== r.passive, o = t !== O && (r === O || i);
    i && this.element.removeEventListener(this.name, this, r), o && this.element.addEventListener(this.name, this, t), this._$AH = t;
  }
  handleEvent(t) {
    typeof this._$AH == "function" ? this._$AH.call(this.options?.host ?? this.element, t) : this._$AH.handleEvent(t);
  }
}
class Xl {
  constructor(t, n, r) {
    this.element = t, this.type = 6, this._$AN = void 0, this._$AM = n, this.options = r;
  }
  get _$AU() {
    return this._$AM._$AU;
  }
  _$AI(t) {
    Ye(this, t);
  }
}
const Zl = { I: tt }, Ql = or.litHtmlPolyfillSupport;
Ql?.(At, tt), (or.litHtmlVersions ??= []).push("3.3.0");
const ec = (e, t, n) => {
  const r = n?.renderBefore ?? t;
  let i = r._$litPart$;
  if (i === void 0) {
    const o = n?.renderBefore ?? null;
    r._$litPart$ = i = new tt(t.insertBefore(Et(), o), o, void 0, n ?? {});
  }
  return i._$AI(e), i;
};
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const sr = globalThis;
let Fe = class extends Le {
  constructor() {
    super(...arguments), this.renderOptions = { host: this }, this._$Do = void 0;
  }
  createRenderRoot() {
    const t = super.createRenderRoot();
    return this.renderOptions.renderBefore ??= t.firstChild, t;
  }
  update(t) {
    const n = this.render();
    this.hasUpdated || (this.renderOptions.isConnected = this.isConnected), super.update(t), this._$Do = ec(n, this.renderRoot, this.renderOptions);
  }
  connectedCallback() {
    super.connectedCallback(), this._$Do?.setConnected(!0);
  }
  disconnectedCallback() {
    super.disconnectedCallback(), this._$Do?.setConnected(!1);
  }
  render() {
    return he;
  }
};
Fe._$litElement$ = !0, Fe.finalized = !0, sr.litElementHydrateSupport?.({ LitElement: Fe });
const tc = sr.litElementPolyfillSupport;
tc?.({ LitElement: Fe });
(sr.litElementVersions ??= []).push("4.2.0");
class nc extends Fl(Fe) {
}
class rc extends nc {
  constructor() {
    super(...arguments), this.disposers = [];
  }
  /**
   * Creates a MobX reaction using the given parameters and disposes it when this element is detached.
   *
   * This should be called from `connectedCallback` to ensure that the reaction is active also if the element is attached again later.
   */
  reaction(t, n, r) {
    this.disposers.push(Qn(t, n, r));
  }
  /**
   * Creates a MobX autorun using the given parameters and disposes it when this element is detached.
   *
   * This should be called from `connectedCallback` to ensure that the reaction is active also if the element is attached again later.
   */
  autorun(t, n) {
    this.disposers.push(Mi(t, n));
  }
  disconnectedCallback() {
    super.disconnectedCallback(), this.disposers.forEach((t) => {
      t();
    }), this.disposers = [];
  }
}
const se = window.Vaadin.copilot._sectionPanelUiState;
if (!se)
  throw new Error("Tried to access copilot section panel ui state before it was initialized.");
let Ee = [];
const Kr = [];
function Wr(e) {
  e.init({
    addPanel: (t) => {
      se.addPanel(t);
    },
    send(t, n) {
      te(t, n);
    }
  });
}
function ic() {
  Ee.push(import("./copilot-log-plugin-CW5qwOmp.js")), Ee.push(import("./copilot-info-plugin-DWRH7j-0.js")), Ee.push(import("./copilot-features-plugin-CfXYzSUe.js")), Ee.push(import("./copilot-feedback-plugin-CZ4xPDyK.js")), Ee.push(import("./copilot-shortcuts-plugin-BmDzHSwy.js"));
}
function oc() {
  {
    const e = `https://cdn.vaadin.com/copilot/${nl}/copilot-plugins${rl}.js`;
    import(
      /* @vite-ignore */
      e
    ).catch((t) => {
      console.warn(`Unable to load plugins from ${e}. Some Copilot features are unavailable.`, t);
    });
  }
}
function ac() {
  Promise.all(Ee).then(() => {
    const e = window.Vaadin;
    if (e.copilot.plugins) {
      const t = e.copilot.plugins;
      e.copilot.plugins.push = (n) => Wr(n), Array.from(t).forEach((n) => {
        Kr.includes(n) || (Wr(n), Kr.push(n));
      });
    }
  }), Ee = [];
}
function Mu(e) {
  return Object.assign({
    expanded: !0,
    expandable: !1,
    panelOrder: 0,
    floating: !1,
    width: 500,
    height: 500,
    floatingPosition: {
      top: 50,
      left: 350
    }
  }, e);
}
function st() {
  return document.body.querySelector("copilot-main");
}
class sc {
  constructor() {
    this.active = !1, this.activate = () => {
      this.active = !0, st()?.focus(), st()?.addEventListener("focusout", this.keepFocusInCopilot);
    }, this.deactivate = () => {
      this.active = !1, st()?.removeEventListener("focusout", this.keepFocusInCopilot);
    }, this.focusInEventListener = (t) => {
      this.active && (t.preventDefault(), t.stopPropagation(), Ge(t.target) || requestAnimationFrame(() => {
        t.target.blur && t.target.blur(), st()?.focus();
      }));
    };
  }
  hostConnectedCallback() {
    const t = this.getApplicationRootElement();
    t && t instanceof HTMLElement && t.addEventListener("focusin", this.focusInEventListener);
  }
  hostDisconnectedCallback() {
    const t = this.getApplicationRootElement();
    t && t instanceof HTMLElement && t.removeEventListener("focusin", this.focusInEventListener);
  }
  getApplicationRootElement() {
    return document.body.firstElementChild;
  }
  keepFocusInCopilot(t) {
    t.preventDefault(), t.stopPropagation(), st()?.focus();
  }
}
const Vt = new sc(), y = window.Vaadin.copilot.eventbus;
if (!y)
  throw new Error("Tried to access copilot eventbus before it was initialized.");
const lt = window.Vaadin.copilot.overlayManager, Lu = {
  DragAndDrop: "Drag and Drop",
  RedoUndo: "Redo/Undo"
}, g = window.Vaadin.copilot._uiState;
if (!g)
  throw new Error("Tried to access copilot ui state before it was initialized.");
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const wo = { CHILD: 2, ELEMENT: 6 }, Eo = (e) => (...t) => ({ _$litDirective$: e, values: t });
class Oo {
  constructor(t) {
  }
  get _$AU() {
    return this._$AM._$AU;
  }
  _$AT(t, n, r) {
    this._$Ct = t, this._$AM = n, this._$Ci = r;
  }
  _$AS(t, n) {
    return this.update(t, n);
  }
  update(t, n) {
    return this.render(...n);
  }
}
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
class Fn extends Oo {
  constructor(t) {
    if (super(t), this.it = O, t.type !== wo.CHILD) throw Error(this.constructor.directiveName + "() can only be used in child bindings");
  }
  render(t) {
    if (t === O || t == null) return this._t = void 0, this.it = t;
    if (t === he) return t;
    if (typeof t != "string") throw Error(this.constructor.directiveName + "() called with a non-string value");
    if (t === this.it) return this._t;
    this.it = t;
    const n = [t];
    return n.raw = n, this._t = { _$litType$: this.constructor.resultType, strings: n, values: [] };
  }
}
Fn.directiveName = "unsafeHTML", Fn.resultType = 1;
const lc = Eo(Fn), nt = window.Vaadin.copilot._machineState;
if (!nt)
  throw new Error("Trying to use stored machine state before it was initialized");
const cc = 5e3;
let Gr = 1;
function Ao(e) {
  g.notifications.includes(e) && (e.dontShowAgain && e.dismissId && uc(e.dismissId), g.removeNotification(e), y.emit("notification-dismissed", e));
}
function xo(e) {
  return nt.getDismissedNotifications().includes(e);
}
function uc(e) {
  xo(e) || nt.addDismissedNotification(e);
}
function dc(e) {
  return !(e.dismissId && (xo(e.dismissId) || g.notifications.find((t) => t.dismissId === e.dismissId)));
}
function So() {
  return Co() ? pe`<vaadin-button
      style="align-self:start;padding:0"
      class="text-white"
      theme="tertiary"
      @click=${(e) => {
    const t = e.target;
    t.disabled = !0, t.innerText = "Restarting...", vc();
  }}>
      Restart now
    </vaadin-button>` : O;
}
function No(e) {
  if (dc(e))
    return fc(e);
}
function fc(e) {
  const t = Gr;
  Gr += 1;
  const n = { ...e, id: t, dontShowAgain: !1, animatingOut: !1 };
  return g.setNotifications([...g.notifications, n]), (e.delay || !e.link && !e.dismissId) && setTimeout(() => {
    Ao(n);
  }, e.delay ?? cc), y.emit("notification-shown", e), n;
}
const pc = /* @__PURE__ */ Object.freeze(/* @__PURE__ */ Object.defineProperty({
  __proto__: null,
  dismissNotification: Ao,
  renderRestartButton: So,
  showNotification: No
}, Symbol.toStringTag, { value: "Module" }));
function Co() {
  return g.idePluginState?.supportedActions?.find((e) => e === "restartApplication");
}
function vc() {
  vn(`${Re}plugin-restart-application`, {}, () => {
  }).catch((e) => {
    le("Error restarting server", e);
  });
}
const Po = window.Vaadin.copilot._previewState;
if (!Po)
  throw new Error("Tried to access copilot preview state before it was initialized.");
const hc = async () => io(() => g.userInfo), zu = async () => (await hc()).vaadiner;
function gc() {
  const e = g.userInfo;
  return !e || e.copilotProjectCannotLeaveLocalhost ? !1 : nt.isSendErrorReportsAllowed();
}
const mc = (e) => {
  le("Unspecified error", e), y.emit("vite-after-update", {});
}, bc = (e, t) => e.error ? (_c(e.error, t), !0) : !1, Yr = (e, t, n) => {
  hn({
    type: Ve.ERROR,
    message: e,
    details: oo(
      pe`<vaadin-details summary="Details" style="color: var(--dev-tools-text-color)"
          ><div>
            <code class="codeblock"><copilot-copy></copilot-copy>${lc(t)}</code>
          </div>
        </vaadin-details>
        ${n !== void 0 ? pe`
              <vaadin-button
                class="text-white"
                theme="tertiary"
                @click="${() => {
        n && y.emit("submit-exception-report-clicked", n);
      }}"
                >Report Issue</vaadin-button
              >
            ` : O} `
    ),
    delay: 3e4
  });
}, $o = (e, t, n, r, i) => {
  const o = g.newVaadinVersionState?.versions?.length === 0;
  i && o ? wc(
    i,
    (a) => {
      Yr(e, t, a);
    },
    e,
    t,
    n
  ) : Yr(e, t), gc() && y.emit("system-info-with-callback", {
    callback: (a) => y.send("copilot-error", {
      message: e,
      details: String(n).replace("	", `
`) + (r ? `
 
Request: 
${JSON.stringify(r)}
` : ""),
      versions: a
    }),
    notify: !1
  }), g.clearOperationWaitsHmrUpdate();
}, _c = (e, t) => {
  $o(
    e.message,
    e.exceptionMessage ?? "",
    e.exceptionStacktrace?.join(`
`) ?? "",
    t,
    e.exceptionReport
  );
};
function yc(e, t) {
  const n = {
    title: t.message,
    nodes: [],
    relevantPairs: [],
    items: []
  };
  $o(e, t.message, t.stack ?? "", void 0, n);
}
function Sn(e) {
  const t = Object.keys(e);
  return t.length === 1 && t.includes("message") || t.length >= 3 && t.includes("message") && t.includes("exceptionMessage") && t.includes("exceptionStacktrace");
}
function le(e, t) {
  const n = Sn(t) ? t.exceptionMessage ?? t.message : t, r = {
    type: Ve.ERROR,
    message: "Copilot internal error",
    details: e + (n ? `
${n}` : "")
  };
  Sn(t) && t.suggestRestart && Co() && (r.details = oo(pe`${e}<br />${n} ${So()}`), r.delay = 3e4), hn(r);
  let i;
  t instanceof Error ? i = t.stack : Sn(t) ? i = t?.exceptionStacktrace?.join(`
`) : i = t?.toString(), y.emit("system-info-with-callback", {
    callback: (o) => y.send("copilot-error", {
      message: `Copilot internal error: ${e}`,
      details: i,
      versions: o
    }),
    notify: !1
  });
}
function Jr(e) {
  return e?.stack?.includes("cdn.vaadin.com/copilot") || e?.stack?.includes("/copilot/copilot/") || e?.stack?.includes("/copilot/copilot-private/");
}
function Do() {
  const e = window.onerror;
  window.onerror = (n, r, i, o, a) => {
    if (Jr(a)) {
      le(n.toString(), a);
      return;
    }
    e && e(n, r, i, o, a);
  }, Xa((n) => {
    Jr(n) && le("", n);
  });
  const t = window.Vaadin.ConsoleErrors;
  Array.isArray(t) && Hn.push(...t), ko((n) => Hn.push(n));
}
function wc(e, t, n, r, i, o) {
  const a = { ...e }, s = window.Vaadin.copilot.tree, l = window.Vaadin.copilot.customComponentHandler;
  a.nodes.forEach((d) => {
    d.node = s.allNodesFlat.find((v) => {
      if (!v.isFlowComponent)
        return !1;
      const h = v.node;
      return h.uiId === d.uiId && h.nodeId === d.nodeId;
    });
  });
  const c = [];
  n && c.push(`Error Message -> ${n}`), r && c.push(`Error Details -> ${r}`), c.push(
    `Active Level -> ${l.getActiveDrillDownContext() ? l.getActiveDrillDownContext()?.nameAndIdentifier : "No active level"}`
  ), a.nodes.length > 0 && (c.push(`
Relevant Nodes:`), a.nodes.forEach((d) => {
    c.push(`${d.relevance} -> ${d.node?.nameAndIdentifier ?? "Node not found"}`);
  })), a.relevantPairs.length > 0 && (c.push(`
Additional Info:`), a.relevantPairs.forEach((d) => {
    c.push(`${d.relevance} -> ${d.value}`);
  }));
  const u = {
    name: "Info",
    content: c.join(`
`)
  };
  a.items.unshift(u), i && a.items.push({
    name: "Stacktrace",
    content: i
  }), y.emit("system-info-with-callback", {
    callback: (d) => {
      a.items.push({
        name: "Versions",
        content: d
      }), t(a);
    },
    notify: !1
  });
}
const Hn = [];
function ko(e) {
  const t = window.Vaadin.ConsoleErrors;
  window.Vaadin.ConsoleErrors = {
    push: (n) => {
      n[0].type !== void 0 && n[0].message !== void 0 ? e({
        type: n[0].type,
        message: n[0].message,
        internal: !!n[0].internal,
        details: n[0].details,
        link: n[0].link
      }) : e({ type: Ve.ERROR, message: n.map((r) => Ec(r)).join(" "), internal: !1 }), t.push(n);
    }
  };
}
function Ec(e) {
  return e.message ? e.message.toString() : e.toString();
}
function Oc(e) {
  hn({
    type: Ve.ERROR,
    message: `Unable to ${e}`,
    details: "Could not find sources for React components, probably because the project is not a React (or Flow) project"
  });
}
const Ac = /* @__PURE__ */ Object.freeze(/* @__PURE__ */ Object.defineProperty({
  __proto__: null,
  catchErrors: ko,
  consoleErrorsQueue: Hn,
  handleBrowserOperationError: yc,
  handleCopilotError: le,
  handleErrorDuringOperation: mc,
  handleServerOperationErrorIfNeeded: bc,
  installErrorHandlers: Do,
  showNotReactFlowProject: Oc
}, Symbol.toStringTag, { value: "Module" })), To = () => {
  xc().then((e) => g.setUserInfo(e)).catch((e) => le("Failed to load userInfo", e));
}, xc = async () => vn(`${Re}get-user-info`, {}, (e) => (delete e.data.reqId, e.data));
y.on("copilot-prokey-received", (e) => {
  To(), e.preventDefault();
});
/**
 * @license
 * Copyright 2020 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const Io = Symbol.for(""), Sc = (e) => {
  if (e?.r === Io) return e?._$litStatic$;
}, Vo = (e) => ({ _$litStatic$: e, r: Io }), Xr = /* @__PURE__ */ new Map(), Nc = (e) => (t, ...n) => {
  const r = n.length;
  let i, o;
  const a = [], s = [];
  let l, c = 0, u = !1;
  for (; c < r; ) {
    for (l = t[c]; c < r && (o = n[c], (i = Sc(o)) !== void 0); ) l += i + t[++c], u = !0;
    c !== r && s.push(o), a.push(l), c++;
  }
  if (c === r && a.push(t[r]), u) {
    const d = a.join("$$lit$$");
    (t = Xr.get(d)) === void 0 && (a.raw = a, Xr.set(d, t = a)), n = s;
  }
  return e(t, ...n);
}, gt = Nc(pe);
/**
 * @license
 * Copyright 2020 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const { I: Cc } = Zl, Uu = (e) => e.strings === void 0, Zr = () => document.createComment(""), ct = (e, t, n) => {
  const r = e._$AA.parentNode, i = t === void 0 ? e._$AB : t._$AA;
  if (n === void 0) {
    const o = r.insertBefore(Zr(), i), a = r.insertBefore(Zr(), i);
    n = new Cc(o, a, e, e.options);
  } else {
    const o = n._$AB.nextSibling, a = n._$AM, s = a !== e;
    if (s) {
      let l;
      n._$AQ?.(e), n._$AM = e, n._$AP !== void 0 && (l = e._$AU) !== a._$AU && n._$AP(l);
    }
    if (o !== i || s) {
      let l = n._$AA;
      for (; l !== o; ) {
        const c = l.nextSibling;
        r.insertBefore(l, i), l = c;
      }
    }
  }
  return n;
}, we = (e, t, n = e) => (e._$AI(t, n), e), Pc = {}, $c = (e, t = Pc) => e._$AH = t, Dc = (e) => e._$AH, Nn = (e) => {
  e._$AP?.(!1, !0);
  let t = e._$AA;
  const n = e._$AB.nextSibling;
  for (; t !== n; ) {
    const r = t.nextSibling;
    t.remove(), t = r;
  }
};
/**
 * @license
 * Copyright 2017 Google LLC
 * SPDX-License-Identifier: BSD-3-Clause
 */
const Qr = (e, t, n) => {
  const r = /* @__PURE__ */ new Map();
  for (let i = t; i <= n; i++) r.set(e[i], i);
  return r;
}, Ro = Eo(class extends Oo {
  constructor(e) {
    if (super(e), e.type !== wo.CHILD) throw Error("repeat() can only be used in text expressions");
  }
  dt(e, t, n) {
    let r;
    n === void 0 ? n = t : t !== void 0 && (r = t);
    const i = [], o = [];
    let a = 0;
    for (const s of e) i[a] = r ? r(s, a) : a, o[a] = n(s, a), a++;
    return { values: o, keys: i };
  }
  render(e, t, n) {
    return this.dt(e, t, n).values;
  }
  update(e, [t, n, r]) {
    const i = Dc(e), { values: o, keys: a } = this.dt(t, n, r);
    if (!Array.isArray(i)) return this.ut = a, o;
    const s = this.ut ??= [], l = [];
    let c, u, d = 0, v = i.length - 1, h = 0, b = o.length - 1;
    for (; d <= v && h <= b; ) if (i[d] === null) d++;
    else if (i[v] === null) v--;
    else if (s[d] === a[h]) l[h] = we(i[d], o[h]), d++, h++;
    else if (s[v] === a[b]) l[b] = we(i[v], o[b]), v--, b--;
    else if (s[d] === a[b]) l[b] = we(i[d], o[b]), ct(e, l[b + 1], i[d]), d++, b--;
    else if (s[v] === a[h]) l[h] = we(i[v], o[h]), ct(e, i[d], i[v]), v--, h++;
    else if (c === void 0 && (c = Qr(a, h, b), u = Qr(s, d, v)), c.has(s[d])) if (c.has(s[v])) {
      const E = u.get(a[h]), S = E !== void 0 ? i[E] : null;
      if (S === null) {
        const Y = ct(e, i[d]);
        we(Y, o[h]), l[h] = Y;
      } else l[h] = we(S, o[h]), ct(e, i[d], S), i[E] = null;
      h++;
    } else Nn(i[v]), v--;
    else Nn(i[d]), d++;
    for (; h <= b; ) {
      const E = ct(e, l[b + 1]);
      we(E, o[h]), l[h++] = E;
    }
    for (; d <= v; ) {
      const E = i[d++];
      E !== null && Nn(E);
    }
    return this.ut = a, $c(e, l), he;
  }
}), zt = /* @__PURE__ */ new Map(), kc = (e) => {
  const n = se.panels.filter((r) => !r.floating && r.panel === e).sort((r, i) => r.panelOrder - i.panelOrder);
  return gt`
    ${Ro(
    n,
    (r) => r.tag,
    (r) => {
      const i = Vo(r.tag);
      return gt` <copilot-section-panel-wrapper panelTag="${i}">
          ${se.shouldRender(r.tag) ? gt`<${i} slot="content"></${i}>` : O}
        </copilot-section-panel-wrapper>`;
    }
  )}
  `;
}, Tc = () => {
  const e = se.panels;
  return gt`
    ${Ro(
    e.filter((t) => t.floating),
    (t) => t.tag,
    (t) => {
      const n = Vo(t.tag);
      return gt`
                        <copilot-section-panel-wrapper panelTag="${n}">
                            <${n} slot="content"></${n}>
                        </copilot-section-panel-wrapper>`;
    }
  )}
  `;
}, Bu = (e) => {
  const t = e.panelTag, n = e.querySelector('[slot="content"]');
  n && e.panelInfo?.panel && zt.set(t, n);
}, Fu = (e) => {
  if (zt.has(e.panelTag)) {
    const t = zt.get(e.panelTag);
    e.querySelector('[slot="content"]').replaceWith(t);
  }
  zt.delete(e.panelTag);
}, N = [];
for (let e = 0; e < 256; ++e)
  N.push((e + 256).toString(16).slice(1));
function Ic(e, t = 0) {
  return (N[e[t + 0]] + N[e[t + 1]] + N[e[t + 2]] + N[e[t + 3]] + "-" + N[e[t + 4]] + N[e[t + 5]] + "-" + N[e[t + 6]] + N[e[t + 7]] + "-" + N[e[t + 8]] + N[e[t + 9]] + "-" + N[e[t + 10]] + N[e[t + 11]] + N[e[t + 12]] + N[e[t + 13]] + N[e[t + 14]] + N[e[t + 15]]).toLowerCase();
}
let Cn;
const Vc = new Uint8Array(16);
function Rc() {
  if (!Cn) {
    if (typeof crypto > "u" || !crypto.getRandomValues)
      throw new Error("crypto.getRandomValues() not supported. See https://github.com/uuidjs/uuid#getrandomvalues-not-supported");
    Cn = crypto.getRandomValues.bind(crypto);
  }
  return Cn(Vc);
}
const jc = typeof crypto < "u" && crypto.randomUUID && crypto.randomUUID.bind(crypto), ei = { randomUUID: jc };
function jo(e, t, n) {
  if (ei.randomUUID && !e)
    return ei.randomUUID();
  e = e || {};
  const r = e.random ?? e.rng?.() ?? Rc();
  if (r.length < 16)
    throw new Error("Random bytes length must be >= 16");
  return r[6] = r[6] & 15 | 64, r[8] = r[8] & 63 | 128, Ic(r);
}
const Ut = [], pt = [], Hu = async (e, t, n) => {
  let r, i;
  t.reqId = jo();
  const o = new Promise((a, s) => {
    r = a, i = s;
  });
  return Ut.push({
    handleMessage(a) {
      if (a?.data?.reqId !== t.reqId)
        return !1;
      try {
        r(n(a));
      } catch (s) {
        i(s);
      }
      return !0;
    }
  }), te(e, t), o;
};
function Mc(e) {
  for (const t of Ut)
    if (t.handleMessage(e))
      return Ut.splice(Ut.indexOf(t), 1), !0;
  if (y.emitUnsafe({ type: e.command, data: e.data }))
    return !0;
  for (const t of Lo())
    if (Mo(t, e))
      return !0;
  return pt.push(e), !1;
}
function Mo(e, t) {
  return e.handleMessage?.call(e, t);
}
function Lc() {
  if (pt.length)
    for (const e of Lo())
      for (let t = 0; t < pt.length; t++)
        Mo(e, pt[t]) && (pt.splice(t, 1), t--);
}
function Lo() {
  const e = document.querySelector("copilot-main");
  return e ? e.renderRoot.querySelectorAll("copilot-section-panel-wrapper *") : [];
}
const zc = "@keyframes bounce{0%{transform:scale(.8)}50%{transform:scale(1.5)}to{transform:scale(1)}}@keyframes bounceLeft{0%{transform:translate(0)}30%{transform:translate(-10px)}50%{transform:translate(0)}70%{transform:translate(-5px)}to{transform:translate(0)}}@keyframes bounceRight{0%{transform:translate(0)}30%{transform:translate(10px)}50%{transform:translate(0)}70%{transform:translate(5px)}to{transform:translate(0)}}@keyframes bounceBottom{0%{transform:translateY(0)}30%{transform:translateY(10px)}50%{transform:translateY(0)}70%{transform:translateY(5px)}to{transform:translateY(0)}}@keyframes around-we-go-again{0%{background-position:0 0,0 0,calc(var(--glow-size) * -.5) calc(var(--glow-size) * -.5),calc(100% + calc(var(--glow-size) * .5)) calc(100% + calc(var(--glow-size) * .5))}25%{background-position:0 0,0 0,calc(100% + calc(var(--glow-size) * .5)) calc(var(--glow-size) * -.5),calc(var(--glow-size) * -.5) calc(100% + calc(var(--glow-size) * .5))}50%{background-position:0 0,0 0,calc(100% + calc(var(--glow-size) * .5)) calc(100% + calc(var(--glow-size) * .5)),calc(var(--glow-size) * -.5) calc(var(--glow-size) * -.5)}75%{background-position:0 0,0 0,calc(var(--glow-size) * -.5) calc(100% + calc(var(--glow-size) * .5)),calc(100% + calc(var(--glow-size) * .5)) calc(var(--glow-size) * -.5)}to{background-position:0 0,0 0,calc(var(--glow-size) * -.5) calc(var(--glow-size) * -.5),calc(100% + calc(var(--glow-size) * .5)) calc(100% + calc(var(--glow-size) * .5))}}@keyframes swirl{0%{rotate:0deg;filter:hue-rotate(20deg)}50%{filter:hue-rotate(-30deg)}to{rotate:360deg;filter:hue-rotate(20deg)}}@keyframes button-focus-in{0%{box-shadow:0 0 0 0 var(--focus-color)}to{box-shadow:0 0 0 var(--focus-size) var(--focus-color)}}@keyframes button-focus-out{0%{box-shadow:0 0 0 var(--focus-size) var(--focus-color)}}@keyframes button-primary-focus-in{0%{box-shadow:0 0 0 0 var(--focus-color)}to{box-shadow:0 0 0 1px var(--background-color),0 0 0 calc(var(--focus-size) + 2px) var(--focus-color)}}@keyframes button-primary-focus-out{0%{box-shadow:0 0 0 1px var(--background-color),0 0 0 calc(var(--focus-size) + 2px) var(--focus-color)}}@keyframes link-focus-in{0%{box-shadow:0 0 0 0 var(--blue-color)}to{box-shadow:0 0 0 var(--focus-size) var(--blue-color)}}@keyframes link-focus-out{0%{box-shadow:0 0 0 var(--focus-size) var(--blue-color)}}@keyframes ping{75%,to{transform:scale(2);opacity:0}}", Uc = "button{align-items:center;-webkit-appearance:none;appearance:none;background:transparent;background-origin:border-box;border:1px solid transparent;border-radius:var(--radius-1);color:var(--body-text-color);display:inline-flex;flex-shrink:0;font:var(--font-button);height:var(--size-m);justify-content:center;outline-offset:calc(var(--focus-size) / -1);padding:0 var(--space-100)}vaadin-button{box-shadow:none;outline-offset:calc(var(--focus-size) / -1)}vaadin-button[theme~=primary]{outline-offset:1px}button:focus,vaadin-button[focus-ring]{animation-delay:0s,.15s;animation-duration:.15s,.45s;animation-name:button-focus-in,button-focus-out;animation-timing-function:cubic-bezier(.2,0,0,1),cubic-bezier(.2,0,0,1);outline:var(--focus-size) solid var(--focus-color)}vaadin-button[theme~=primary][focus-ring]{animation-name:button-primary-focus-in,button-primary-focus-out}button.icon{padding:0;width:var(--size-m)}button.icon span:has(svg){display:contents}button.primary{background:var(--primary-color);color:var(--primary-contrast-text-color)}button .prefix,button .suffix{align-items:center;display:flex;height:var(--size-m);justify-content:center;width:var(--size-m)}button:has(.prefix){padding-inline-start:0}button:has(.suffix){padding-inline-end:0}button svg{height:var(--icon-size-s);width:var(--icon-size-s)}button:active:not([disabled]){background:var(--active-color)}button[disabled]{opacity:.5}button[hidden]{display:none}", Bc = "code.codeblock{background:var(--contrast-color-5);border-radius:var(--radius-2);display:block;font-family:var(--monospace-font-family);font-size:var(--font-size-1);line-height:var(--line-height-1);overflow:hidden;padding:calc((var(--size-m) - var(--line-height-1)) / 2) var(--size-m) calc((var(--size-m) - var(--line-height-1)) / 2) var(--space-100);position:relative;text-overflow:ellipsis;white-space:pre;min-height:var(--line-height-1)}copilot-copy{position:absolute;right:0;top:0}div.message.error code.codeblock copilot-copy svg{color:#ffffffb3}", Fc = ":host{--gray-h: 220;--gray-s: 30%;--gray-l: 30%;--gray-hsl: var(--gray-h) var(--gray-s) var(--gray-l);--gray: hsl(var(--gray-hsl));--gray-50: hsl(var(--gray-hsl) / .05);--gray-100: hsl(var(--gray-hsl) / .1);--gray-150: hsl(var(--gray-hsl) / .16);--gray-200: hsl(var(--gray-hsl) / .24);--gray-250: hsl(var(--gray-hsl) / .34);--gray-300: hsl(var(--gray-hsl) / .46);--gray-350: hsl(var(--gray-hsl) / .6);--gray-400: hsl(var(--gray-hsl) / .7);--gray-450: hsl(var(--gray-hsl) / .8);--gray-500: hsl(var(--gray-hsl) / .9);--gray-550: hsl(var(--gray-hsl));--gray-600: hsl(var(--gray-h) var(--gray-s) calc(var(--gray-l) - 2%));--gray-650: hsl(var(--gray-h) var(--gray-s) calc(var(--gray-l) - 4%));--gray-700: hsl(var(--gray-h) var(--gray-s) calc(var(--gray-l) - 8%));--gray-750: hsl(var(--gray-h) var(--gray-s) calc(var(--gray-l) - 12%));--gray-800: hsl(var(--gray-h) var(--gray-s) calc(var(--gray-l) - 20%));--gray-850: hsl(var(--gray-h) var(--gray-s) calc(var(--gray-l) - 23%));--gray-900: hsl(var(--gray-h) var(--gray-s) calc(var(--gray-l) - 30%));--blue-h: 220;--blue-s: 90%;--blue-l: 53%;--blue-hsl: var(--blue-h) var(--blue-s) var(--blue-l);--blue: hsl(var(--blue-hsl));--blue-50: hsl(var(--blue-hsl) / .05);--blue-100: hsl(var(--blue-hsl) / .1);--blue-150: hsl(var(--blue-hsl) / .2);--blue-200: hsl(var(--blue-hsl) / .3);--blue-250: hsl(var(--blue-hsl) / .4);--blue-300: hsl(var(--blue-hsl) / .5);--blue-350: hsl(var(--blue-hsl) / .6);--blue-400: hsl(var(--blue-hsl) / .7);--blue-450: hsl(var(--blue-hsl) / .8);--blue-500: hsl(var(--blue-hsl) / .9);--blue-550: hsl(var(--blue-hsl));--blue-600: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) - 4%));--blue-650: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) - 8%));--blue-700: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) - 12%));--blue-750: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) - 15%));--blue-800: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) - 18%));--blue-850: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) - 24%));--blue-900: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) - 27%));--purple-h: 246;--purple-s: 90%;--purple-l: 60%;--purple-hsl: var(--purple-h) var(--purple-s) var(--purple-l);--purple: hsl(var(--purple-hsl));--purple-50: hsl(var(--purple-hsl) / .05);--purple-100: hsl(var(--purple-hsl) / .1);--purple-150: hsl(var(--purple-hsl) / .2);--purple-200: hsl(var(--purple-hsl) / .3);--purple-250: hsl(var(--purple-hsl) / .4);--purple-300: hsl(var(--purple-hsl) / .5);--purple-350: hsl(var(--purple-hsl) / .6);--purple-400: hsl(var(--purple-hsl) / .7);--purple-450: hsl(var(--purple-hsl) / .8);--purple-500: hsl(var(--purple-hsl) / .9);--purple-550: hsl(var(--purple-hsl));--purple-600: hsl(var(--purple-h) calc(var(--purple-s) - 4%) calc(var(--purple-l) - 2%));--purple-650: hsl(var(--purple-h) calc(var(--purple-s) - 8%) calc(var(--purple-l) - 4%));--purple-700: hsl(var(--purple-h) calc(var(--purple-s) - 15%) calc(var(--purple-l) - 7%));--purple-750: hsl(var(--purple-h) calc(var(--purple-s) - 23%) calc(var(--purple-l) - 11%));--purple-800: hsl(var(--purple-h) calc(var(--purple-s) - 24%) calc(var(--purple-l) - 15%));--purple-850: hsl(var(--purple-h) calc(var(--purple-s) - 24%) calc(var(--purple-l) - 19%));--purple-900: hsl(var(--purple-h) calc(var(--purple-s) - 27%) calc(var(--purple-l) - 23%));--green-h: 150;--green-s: 80%;--green-l: 42%;--green-hsl: var(--green-h) var(--green-s) var(--green-l);--green: hsl(var(--green-hsl));--green-50: hsl(var(--green-hsl) / .05);--green-100: hsl(var(--green-hsl) / .1);--green-150: hsl(var(--green-hsl) / .2);--green-200: hsl(var(--green-hsl) / .3);--green-250: hsl(var(--green-hsl) / .4);--green-300: hsl(var(--green-hsl) / .5);--green-350: hsl(var(--green-hsl) / .6);--green-400: hsl(var(--green-hsl) / .7);--green-450: hsl(var(--green-hsl) / .8);--green-500: hsl(var(--green-hsl) / .9);--green-550: hsl(var(--green-hsl));--green-600: hsl(var(--green-h) var(--green-s) calc(var(--green-l) - 2%));--green-650: hsl(var(--green-h) var(--green-s) calc(var(--green-l) - 4%));--green-700: hsl(var(--green-h) var(--green-s) calc(var(--green-l) - 8%));--green-750: hsl(var(--green-h) var(--green-s) calc(var(--green-l) - 12%));--green-800: hsl(var(--green-h) var(--green-s) calc(var(--green-l) - 15%));--green-850: hsl(var(--green-h) var(--green-s) calc(var(--green-l) - 19%));--green-900: hsl(var(--green-h) var(--green-s) calc(var(--green-l) - 23%));--yellow-h: 38;--yellow-s: 98%;--yellow-l: 64%;--yellow-hsl: var(--yellow-h) var(--yellow-s) var(--yellow-l);--yellow: hsl(var(--yellow-hsl));--yellow-50: hsl(var(--yellow-hsl) / .07);--yellow-100: hsl(var(--yellow-hsl) / .12);--yellow-150: hsl(var(--yellow-hsl) / .2);--yellow-200: hsl(var(--yellow-hsl) / .3);--yellow-250: hsl(var(--yellow-hsl) / .4);--yellow-300: hsl(var(--yellow-hsl) / .5);--yellow-350: hsl(var(--yellow-hsl) / .6);--yellow-400: hsl(var(--yellow-hsl) / .7);--yellow-450: hsl(var(--yellow-hsl) / .8);--yellow-500: hsl(var(--yellow-hsl) / .9);--yellow-550: hsl(var(--yellow-hsl));--yellow-600: hsl(var(--yellow-h) var(--yellow-s) calc(var(--yellow-l) - 5%));--yellow-650: hsl(var(--yellow-h) var(--yellow-s) calc(var(--yellow-l) - 10%));--yellow-700: hsl(var(--yellow-h) var(--yellow-s) calc(var(--yellow-l) - 15%));--yellow-750: hsl(var(--yellow-h) var(--yellow-s) calc(var(--yellow-l) - 20%));--yellow-800: hsl(var(--yellow-h) var(--yellow-s) calc(var(--yellow-l) - 25%));--yellow-850: hsl(var(--yellow-h) var(--yellow-s) calc(var(--yellow-l) - 30%));--yellow-900: hsl(var(--yellow-h) var(--yellow-s) calc(var(--yellow-l) - 35%));--red-h: 355;--red-s: 75%;--red-l: 55%;--red-hsl: var(--red-h) var(--red-s) var(--red-l);--red: hsl(var(--red-hsl));--red-50: hsl(var(--red-hsl) / .05);--red-100: hsl(var(--red-hsl) / .1);--red-150: hsl(var(--red-hsl) / .2);--red-200: hsl(var(--red-hsl) / .3);--red-250: hsl(var(--red-hsl) / .4);--red-300: hsl(var(--red-hsl) / .5);--red-350: hsl(var(--red-hsl) / .6);--red-400: hsl(var(--red-hsl) / .7);--red-450: hsl(var(--red-hsl) / .8);--red-500: hsl(var(--red-hsl) / .9);--red-550: hsl(var(--red-hsl));--red-600: hsl(var(--red-h) calc(var(--red-s) - 5%) calc(var(--red-l) - 2%));--red-650: hsl(var(--red-h) calc(var(--red-s) - 10%) calc(var(--red-l) - 4%));--red-700: hsl(var(--red-h) calc(var(--red-s) - 15%) calc(var(--red-l) - 8%));--red-750: hsl(var(--red-h) calc(var(--red-s) - 20%) calc(var(--red-l) - 12%));--red-800: hsl(var(--red-h) calc(var(--red-s) - 25%) calc(var(--red-l) - 15%));--red-850: hsl(var(--red-h) calc(var(--red-s) - 30%) calc(var(--red-l) - 19%));--red-900: hsl(var(--red-h) calc(var(--red-s) - 35%) calc(var(--red-l) - 23%));--codeblock-bg: #f4f4f4;--background-color: rgba(255, 255, 255, .87);--primary-color: #0368de;--input-border-color: rgba(0, 0, 0, .42);--divider-primary-color: rgba(0, 0, 0, .1);--divider-secondary-color: rgba(0, 0, 0, .05);--switch-active-color: #0b754f;--switch-inactive-color: #666666;--body-text-color: rgba(0, 0, 0, .87);--secondary-text-color: rgba(0, 0, 0, .6);--primary-contrast-text-color: white;--active-color: rgba(3, 104, 222, .1);--focus-color: #0377ff;--hover-color: rgba(0, 0, 0, .05);--success-color: var(--success-color-80);--error-color: var(--error-color-70);--warning-color: #8a6c1e;--success-color-5: #f0fffa;--success-color-10: #eafaf4;--success-color-20: #d2f0e5;--success-color-30: #8ce4c5;--success-color-40: #39c693;--success-color-50: #1ba875;--success-color-60: #0e9c69;--success-color-70: #0d8b5e;--success-color-80: #066845;--success-color-90: #004d31;--error-color-5: #fff5f6;--error-color-10: #ffedee;--error-color-20: #ffd0d4;--error-color-30: #f8a8ae;--error-color-40: #ff707a;--error-color-50: #ff3a49;--error-color-60: #ff0013;--error-color-70: #ce0010;--error-color-80: #97000b;--error-color-90: #680008;--contrast-color-5: rgba(0, 0, 0, .05);--contrast-color-10: rgba(0, 0, 0, .1);--contrast-color-20: rgba(0, 0, 0, .2);--contrast-color-30: rgba(0, 0, 0, .3);--contrast-color-40: rgba(0, 0, 0, .4);--contrast-color-50: rgba(0, 0, 0, .5);--contrast-color-60: rgba(0, 0, 0, .6);--contrast-color-70: rgba(0, 0, 0, .7);--contrast-color-80: rgba(0, 0, 0, .8);--contrast-color-90: rgba(0, 0, 0, .9);--contrast-color-100: black;--blue-color: #0368de;--violet-color: #7b2bff}:host(.dark){--gray-s: 15%;--gray-l: 70%;--gray-600: hsl(var(--gray-h) calc(var(--gray-s) - 2%) calc(var(--gray-l) + 6%));--gray-650: hsl(var(--gray-h) calc(var(--gray-s) - 5%) calc(var(--gray-l) + 14%));--gray-700: hsl(var(--gray-h) calc(var(--gray-s) - 2%) calc(var(--gray-l) + 26%));--gray-750: hsl(var(--gray-h) calc(var(--gray-s) - 2%) calc(var(--gray-l) + 36%));--gray-800: hsl(var(--gray-h) calc(var(--gray-s) - 2%) calc(var(--gray-l) + 48%));--gray-850: hsl(var(--gray-h) calc(var(--gray-s) - 2%) calc(var(--gray-l) + 62%));--gray-900: hsl(var(--gray-h) calc(var(--gray-s) - 2%) calc(var(--gray-l) + 70%));--blue-s: 90%;--blue-l: 58%;--blue-600: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) + 6%));--blue-650: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) + 12%));--blue-700: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) + 17%));--blue-750: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) + 22%));--blue-800: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) + 28%));--blue-850: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) + 35%));--blue-900: hsl(var(--blue-h) var(--blue-s) calc(var(--blue-l) + 43%));--purple-600: hsl(var(--purple-h) var(--purple-s) calc(var(--purple-l) + 4%));--purple-650: hsl(var(--purple-h) var(--purple-s) calc(var(--purple-l) + 9%));--purple-700: hsl(var(--purple-h) var(--purple-s) calc(var(--purple-l) + 12%));--purple-750: hsl(var(--purple-h) var(--purple-s) calc(var(--purple-l) + 18%));--purple-800: hsl(var(--purple-h) var(--purple-s) calc(var(--purple-l) + 24%));--purple-850: hsl(var(--purple-h) var(--purple-s) calc(var(--purple-l) + 29%));--purple-900: hsl(var(--purple-h) var(--purple-s) calc(var(--purple-l) + 33%));--green-600: hsl(calc(var(--green-h) - 1) calc(var(--green-s) - 5%) calc(var(--green-l) + 5%));--green-650: hsl(calc(var(--green-h) - 2) calc(var(--green-s) - 10%) calc(var(--green-l) + 12%));--green-700: hsl(calc(var(--green-h) - 4) calc(var(--green-s) - 15%) calc(var(--green-l) + 20%));--green-750: hsl(calc(var(--green-h) - 6) calc(var(--green-s) - 20%) calc(var(--green-l) + 29%));--green-800: hsl(calc(var(--green-h) - 8) calc(var(--green-s) - 25%) calc(var(--green-l) + 37%));--green-850: hsl(calc(var(--green-h) - 10) calc(var(--green-s) - 30%) calc(var(--green-l) + 42%));--green-900: hsl(calc(var(--green-h) - 12) calc(var(--green-s) - 35%) calc(var(--green-l) + 48%));--yellow-600: hsl(calc(var(--yellow-h) + 1) var(--yellow-s) calc(var(--yellow-l) + 4%));--yellow-650: hsl(calc(var(--yellow-h) + 2) var(--yellow-s) calc(var(--yellow-l) + 7%));--yellow-700: hsl(calc(var(--yellow-h) + 4) var(--yellow-s) calc(var(--yellow-l) + 11%));--yellow-750: hsl(calc(var(--yellow-h) + 6) var(--yellow-s) calc(var(--yellow-l) + 16%));--yellow-800: hsl(calc(var(--yellow-h) + 8) var(--yellow-s) calc(var(--yellow-l) + 20%));--yellow-850: hsl(calc(var(--yellow-h) + 10) var(--yellow-s) calc(var(--yellow-l) + 24%));--yellow-900: hsl(calc(var(--yellow-h) + 12) var(--yellow-s) calc(var(--yellow-l) + 29%));--red-600: hsl(calc(var(--red-h) - 1) calc(var(--red-s) - 5%) calc(var(--red-l) + 3%));--red-650: hsl(calc(var(--red-h) - 2) calc(var(--red-s) - 10%) calc(var(--red-l) + 7%));--red-700: hsl(calc(var(--red-h) - 4) calc(var(--red-s) - 15%) calc(var(--red-l) + 14%));--red-750: hsl(calc(var(--red-h) - 6) calc(var(--red-s) - 20%) calc(var(--red-l) + 19%));--red-800: hsl(calc(var(--red-h) - 8) calc(var(--red-s) - 25%) calc(var(--red-l) + 24%));--red-850: hsl(calc(var(--red-h) - 10) calc(var(--red-s) - 30%) calc(var(--red-l) + 30%));--red-900: hsl(calc(var(--red-h) - 12) calc(var(--red-s) - 35%) calc(var(--red-l) + 36%));--codeblock-bg: var(--gray-100);--background-color: rgba(0, 0, 0, .87);--primary-color: white;--input-border-color: rgba(255, 255, 255, .42);--divider-primary-color: rgba(255, 255, 255, .2);--divider-secondary-color: rgba(255, 255, 255, .1);--body-text-color: white;--secondary-text-color: rgba(255, 255, 255, .7);--primary-contrast-text-color: rgba(0, 0, 0, .87);--active-color: rgba(255, 255, 255, .15);--focus-color: rgba(255, 255, 255, .5);--hover-color: rgba(255, 255, 255, .1);--success-color: var(--success-color-50);--error-color: var(--error-color-50);--warning-color: #fec941;--success-color-5: #004d31;--success-color-10: #066845;--success-color-20: #0d8b5e;--success-color-30: #0e9c69;--success-color-40: #1ba875;--success-color-50: #39c693;--success-color-60: #8ce4c5;--success-color-70: #d2f0e5;--success-color-80: #eafaf4;--success-color-90: #f0fffa;--error-color-5: #680008;--error-color-10: #97000b;--error-color-20: #ce0010;--error-color-30: #ff0013;--error-color-40: #ff3a49;--error-color-50: #ff707a;--error-color-60: #f8a8ae;--error-color-70: #ffd0d4;--error-color-80: #ffedee;--error-color-90: #fff5f6;--contrast-color-5: rgba(255, 255, 255, .05);--contrast-color-10: rgba(255, 255, 255, .1);--contrast-color-20: rgba(255, 255, 255, .2);--contrast-color-30: rgba(255, 255, 255, .3);--contrast-color-40: rgba(255, 255, 255, .4);--contrast-color-50: rgba(255, 255, 255, .5);--contrast-color-60: rgba(255, 255, 255, .6);--contrast-color-70: rgba(255, 255, 255, .7);--contrast-color-80: rgba(255, 255, 255, .8);--contrast-color-90: rgba(255, 255, 255, .9);--contrast-color-100: white;--blue-color: #95c6ff;--violet-color: #cbb4ff}.bg-blue{background-color:var(--blue-color)}.bg-error{background-color:var(--error-color)}.bg-success{background-color:var(--success-color)}.bg-violet{background-color:var(--violet-color)}.bg-warning{background-color:var(--warning-color)}.blue-text{color:var(--blue-color)}.error-text{color:var(--error-color)}.success-text{color:var(--success-color)}.violet-text{color:var(--violet-color)}.warning-text{color:var(--warning-color)}", Hc = ":host{--vaadin-select-label-font-size: var(--font-size-1);--vaadin-checkbox-label-font-size: var(--font-size-1);--vaadin-input-field-value-font-size: var(--font-xsmall);--vaadin-button-border-radius: var(--radius-1);--vaadin-button-font-size: var(--font-size-1);--vaadin-button-font-weight: var(--font-weight-semibold);--vaadin-button-margin: 0;--vaadin-button-padding: 0 var(--space-100);--vaadin-button-primary-font-weight: var(--font-weight-semibold);--vaadin-button-primary-text-color: var(--primary-contrast-text-color);--vaadin-button-tertiary-font-weight: var(--font-weight-semibold);--vaadin-button-tertiary-padding: 0 var(--space-100);--vaadin-input-field-background: transparent;--vaadin-input-field-border-color: var(--input-border-color);--vaadin-input-field-border-radius: var(--radius-1);--vaadin-input-field-border-width: 1px;--vaadin-input-field-height: var(--size-m);--vaadin-input-field-label-font-size: var(--font-size-1);--vaadin-input-field-label-font-weight: var(--font-weight-medium);--vaadin-input-field-helper-font-size: var(--font-size-1);--vaadin-input-field-helper-font-weight: var(--font-weight-normal);--vaadin-input-field-helper-spacing: var(--space-50);--vaadin-input-field-hover-highlight-opacity: 0;--vaadin-input-field-hovered-label-color: var(--body-text-color)}", qc = "vaadin-dialog-overlay::part(overlay){background:var(--background-color);-webkit-backdrop-filter:var(--surface-backdrop-filter);backdrop-filter:var(--surface-backdrop-filter);border:1px solid var(--contrast-color-5);border-radius:var(--radius-2);box-shadow:var(--surface-box-shadow-1)}vaadin-dialog-overlay::part(header){background:none;border-bottom:1px solid var(--divider-primary-color);box-sizing:border-box;font:var(--font-xsmall-semibold);min-height:var(--size-xl);padding:var(--space-50) var(--space-50) var(--space-50) var(--space-150)}vaadin-dialog-overlay h2{font:var(--font-xsmall-bold);margin:0;padding:0}vaadin-dialog-overlay::part(content){font:var(--font-xsmall);padding:var(--space-150)}vaadin-dialog-overlay::part(footer){background:none;padding:var(--space-100)}vaadin-dialog-overlay.ai-dialog::part(overlay){max-width:20rem}vaadin-dialog-overlay.ai-dialog::part(header){border:none}vaadin-dialog-overlay.ai-dialog [slot=header-content] svg{color:var(--blue-color)}vaadin-dialog-overlay.ai-dialog::part(content){display:flex;flex-direction:column;gap:var(--space-200)}vaadin-dialog-overlay.ai-dialog p{margin:0}vaadin-dialog-overlay.ai-dialog label:has(input[type=checkbox]){align-items:center;display:flex}vaadin-dialog-overlay.ai-dialog input[type=checkbox]{height:.875rem;margin:calc((var(--size-m) - .875rem) / 2);width:.875rem}vaadin-dialog-overlay.ai-dialog button.primary{min-width:calc(var(--size-m) * 2)}vaadin-dialog-overlay.custom-component-api-dialog-overlay::part(overlay){width:30em}vaadin-dialog-overlay.custom-component-api-dialog-overlay::part(header-content){width:unset;justify-content:unset;flex:unset}vaadin-dialog-overlay.custom-component-api-dialog-overlay::part(title){font-size:var(--font-size-2)}vaadin-dialog-overlay.custom-component-api-dialog-overlay::part(header){border-bottom:unset;justify-content:space-between}vaadin-dialog-overlay.custom-component-api-dialog-overlay::part(content){padding:var(--space-100);max-height:250px;overflow:auto}vaadin-dialog-overlay.custom-component-api-dialog-overlay div.item-content{display:flex;justify-content:center;align-items:start;flex-direction:column}vaadin-dialog-overlay.custom-component-api-dialog-overlay div.method-row-container{display:flex;justify-content:space-between;width:100%;align-items:center}vaadin-dialog-overlay.custom-component-api-dialog-overlay div.method-row-container div.class-method-name{padding-left:var(--space-150)}vaadin-dialog-overlay.custom-component-api-dialog-overlay div.method-row-container div.action-btn-container{width:150px}vaadin-dialog-overlay.custom-component-api-dialog-overlay div.method-row-container div.action-btn-container button.action-btn.selected{color:var(--selection-color)}vaadin-dialog-overlay.edit-component-dialog-overlay{width:25em}vaadin-dialog-overlay.edit-component-dialog-overlay #component-icon{width:75px}vaadin-dialog-overlay.report-exception-dialog{z-index:calc(var(--copilot-notifications-container-z-index) + 1)}vaadin-dialog-overlay.report-exception-dialog::part(overlay){height:600px}vaadin-dialog-overlay.report-exception-dialog vaadin-text-area{width:100%;min-height:120px}vaadin-dialog-overlay.report-exception-dialog .list-preview-container{display:flex;flex-direction:row;gap:var(--space-100);margin-top:var(--space-50)}vaadin-dialog-overlay.report-exception-dialog .left-menu{display:flex;flex-direction:column;min-width:200px;width:200px}vaadin-dialog-overlay.report-exception-dialog .right-menu{display:flex;flex-direction:column;white-space:break-spaces;overflow:auto;border-radius:var(--radius-2);align-items:start;height:300px;width:800px}vaadin-dialog-overlay.report-exception-dialog .right-menu pre{margin:0}vaadin-dialog-overlay.report-exception-dialog vaadin-item div.item-content{display:inline-block}vaadin-dialog-overlay.report-exception-dialog vaadin-item div.item-content span{max-width:150px;white-space:nowrap;text-overflow:ellipsis;overflow:hidden;display:block}vaadin-dialog-overlay.report-exception-dialog vaadin-item div.item-content span.item-description{color:var(--secondary-text-color)}vaadin-dialog-overlay.report-exception-dialog vaadin-item[selected]{background-color:var(--active-color);border-left:2px solid var(--primary-color)}vaadin-dialog-overlay.report-exception-dialog vaadin-item::part(content){display:flex;align-items:center;gap:var(--space-100)}vaadin-dialog-overlay.report-exception-dialog vaadin-item::part(checkmark){display:none}vaadin-dialog-overlay.report-exception-dialog div.section-title{color:var(--secondary-text-color);padding-top:var(--space-50);padding-bottom:var(--space-50)}vaadin-dialog-overlay.report-exception-dialog code.codeblock{width:100%;box-sizing:border-box}", Kc = ':is(vaadin-context-menu-overlay,vaadin-menu-bar-overlay,vaadin-select-overlay){z-index:var(--z-index-popover)}:is(vaadin-context-menu-overlay,vaadin-menu-bar-overlay,vaadin-select-overlay):first-of-type{padding-top:0}:is(vaadin-combo-box-overlay,vaadin-context-menu-overlay,vaadin-menu-bar-overlay,vaadin-popover-overlay,vaadin-select-overlay,vaadin-tooltip-overlay,vaadin-multi-select-combo-box-overlay)::part(overlay){background:var(--background-color);-webkit-backdrop-filter:var(--surface-backdrop-filter);backdrop-filter:var(--surface-backdrop-filter);border-radius:var(--radius-1);box-shadow:var(--surface-box-shadow-1);margin-top:0}:is(vaadin-context-menu-overlay,vaadin-menu-bar-overlay,vaadin-select-overlay)::part(content){padding:var(--space-50)}:is(vaadin-combo-box-item,vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item){--_lumo-item-selected-icon-display: none;align-items:center;border-radius:var(--radius-1);color:var(--body-text-color);cursor:default;display:flex;font:var(--font-xsmall-medium);min-height:0;padding:calc((var(--size-m) - var(--line-height-1)) / 2) var(--space-100)}:is(vaadin-combo-box-item,vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item,vaadin-multi-select-combo-box-item)[disabled],:is(vaadin-combo-box-item,vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item,vaadin-multi-select-combo-box-item)[disabled] .hint,:is(vaadin-combo-box-item,vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item,vaadin-multi-select-combo-box-item)[disabled] vaadin-icon{color:var(--lumo-disabled-text-color)}:is(vaadin-context-menu-item,vaadin-menu-bar-item):hover:not([disabled]),:is(vaadin-context-menu-item,vaadin-menu-bar-item)[expanded]:not([disabled]){background:var(--hover-color)}:is(vaadin-combo-box-item,vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item,vaadin-multi-select-combo-box-item)[focus-ring]{outline:2px solid var(--selection-color);outline-offset:-2px}:is(vaadin-combo-box-item,vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item,vaadin-multi-select-combo-box-item):is([aria-haspopup=true]):after{align-items:center;display:flex;height:var(--icon-size-m);justify-content:center;margin:0;padding:0;width:var(--icon-size-m)}:is(vaadin-combo-box-item,vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item,vaadin-multi-select-combo-box-item).danger{color:var(--error-color);--color: currentColor}:is(vaadin-combo-box-item,vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item,vaadin-multi-select-combo-box-item)::part(content){display:flex;align-items:center;gap:var(--space-100)}:is(vaadin-combo-box-item,vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item,vaadin-multi-select-combo-box-item) vaadin-icon{width:1em;height:1em;padding:0;color:var(--color)}:is(vaadin-context-menu-overlay,vaadin-menu-bar-overlay,vaadin-select-overlay) hr{margin:var(--space-50)}:is(vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item)>svg:first-child{color:var(--secondary-text-color)}:is(vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item) .label{margin-inline-end:auto;padding-inline-end:var(--space-300)}:is(vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item) .hint{color:var(--secondary-text-color)}:is(vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item) kbd{align-items:center;display:inline-flex;border-radius:var(--radius-1);font:var(--font-xsmall);outline:1px solid var(--divider-primary-color);outline-offset:-1px;padding:0 var(--space-50)}:is(vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item) .switch{align-items:center;border-radius:9999px;box-sizing:border-box;display:flex;height:.75rem;padding:var(--space-25);width:1.25rem}:is(vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item) .switch.on{background:var(--switch-active-color);justify-content:end}:is(vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item) .switch.off{background:var(--switch-inactive-color);justify-content:start}:is(vaadin-context-menu-item,vaadin-menu-bar-item,vaadin-select-item) .switch:before{background:#fff;border-radius:9999px;content:"";display:flex;height:.5rem;box-shadow:var(--shadow-m);width:.5rem}copilot-activation-button-user-info,copilot-activation-button-development-workflow,copilot-activation-button-feedback{display:contents}:is(copilot-activation-button-user-info,copilot-activation-button-development-workflow,copilot-activation-button-feedback) .prefix,:is(copilot-activation-button-user-info,copilot-activation-button-development-workflow,copilot-activation-button-feedback) .suffix{align-items:center;display:flex;height:var(--icon-size-m);justify-content:center;width:var(--icon-size-m)}:is(copilot-activation-button-user-info,copilot-activation-button-development-workflow,copilot-activation-button-feedback) .content{display:flex;flex-direction:column;margin-inline-end:auto}:is(copilot-activation-button-user-info,copilot-activation-button-development-workflow,copilot-activation-button-feedback) .error{color:var(--error-color)}:is(copilot-activation-button-user-info,copilot-activation-button-development-workflow,copilot-activation-button-feedback) .warning{color:var(--warning-color)}:is(copilot-activation-button-user-info,copilot-activation-button-development-workflow,copilot-activation-button-feedback) .portrait{background-size:cover;border-radius:9999px;height:var(--icon-size-m);width:var(--icon-size-m)}:is(copilot-activation-button-user-info,copilot-activation-button-development-workflow,copilot-activation-button-feedback) .dot{background-color:currentColor;border-radius:9999px;height:var(--space-75);width:var(--space-75)}vaadin-menu-bar-item[aria-selected=true]>svg:first-child{color:var(--blue-color)}:is(copilot-alignment-overlay)::part(content){padding:0}', Wc = "vaadin-popover-overlay::part(overlay){background:var(--surface);font:var(--font-xsmall)}vaadin-popover-overlay{--vaadin-button-font-size: var(--font-size-1);--vaadin-button-height: var(--line-height-4)}", Gc = "vaadin-select::part(input-field){padding-inline-start:0}vaadin-select-value-button{padding:0}", Yc = ':host{--font-family: "Manrope", sans-serif;--monospace-font-family: Inconsolata, Monaco, Consolas, Courier New, Courier, monospace;--font-size-0: .6875rem;--font-size-1: .75rem;--font-size-2: .875rem;--font-size-3: 1rem;--font-size-4: 1.125rem;--font-size-5: 1.25rem;--font-size-6: 1.375rem;--font-size-7: 1.5rem;--line-height-0: 1rem;--line-height-1: 1.125rem;--line-height-2: 1.25rem;--line-height-3: 1.5rem;--line-height-4: 1.75rem;--line-height-5: 2rem;--line-height-6: 2.25rem;--line-height-7: 2.5rem;--font-weight-normal: 440;--font-weight-medium: 540;--font-weight-semibold: 640;--font-weight-bold: 740;--font: normal var(--font-weight-normal) var(--font-size-3) / var(--line-height-3) var(--font-family);--font-medium: normal var(--font-weight-medium) var(--font-size-3) / var(--line-height-3) var(--font-family);--font-semibold: normal var(--font-weight-semibold) var(--font-size-3) / var(--line-height-3) var(--font-family);--font-bold: normal var(--font-weight-bold) var(--font-size-3) / var(--line-height-3) var(--font-family);--font-small: normal var(--font-weight-normal) var(--font-size-2) / var(--line-height-2) var(--font-family);--font-small-medium: normal var(--font-weight-medium) var(--font-size-2) / var(--line-height-2) var(--font-family);--font-small-semibold: normal var(--font-weight-semibold) var(--font-size-2) / var(--line-height-2) var(--font-family);--font-small-bold: normal var(--font-weight-bold) var(--font-size-2) / var(--line-height-2) var(--font-family);--font-xsmall: normal var(--font-weight-normal) var(--font-size-1) / var(--line-height-1) var(--font-family);--font-xsmall-medium: normal var(--font-weight-medium) var(--font-size-1) / var(--line-height-1) var(--font-family);--font-xsmall-semibold: normal var(--font-weight-semibold) var(--font-size-1) / var(--line-height-1) var(--font-family);--font-xsmall-bold: normal var(--font-weight-bold) var(--font-size-1) / var(--line-height-1) var(--font-family);--font-xxsmall: normal var(--font-weight-normal) var(--font-size-0) / var(--line-height-0) var(--font-family);--font-xxsmall-medium: normal var(--font-weight-medium) var(--font-size-0) / var(--line-height-0) var(--font-family);--font-xxsmall-semibold: normal var(--font-weight-semibold) var(--font-size-0) / var(--line-height-0) var(--font-family);--font-xxsmall-bold: normal var(--font-weight-bold) var(--font-size-0) / var(--line-height-0) var(--font-family);--font-button: normal var(--font-weight-semibold) var(--font-size-1) / var(--line-height-1) var(--font-family);--font-tooltip: normal var(--font-weight-medium) var(--font-size-1) / var(--line-height-2) var(--font-family);--z-index-component-selector: 100;--z-index-floating-panel: 101;--z-index-drawer: 150;--z-index-opened-drawer: 151;--z-index-spotlight: 200;--z-index-popover: 300;--z-index-activation-button: 1000;--copilot-notifications-container-z-index: 10000;--duration-1: .1s;--duration-2: .2s;--duration-3: .3s;--duration-4: .4s;--button-background: var(--gray-100);--button-background-hover: var(--gray-150);--focus-size: 2px;--icon-size-xs: .75rem;--icon-size-s: 1rem;--icon-size-m: 1.125rem;--shadow-xs: 0 1px 2px 0 rgb(0 0 0 / .05);--shadow-s: 0 1px 3px 0 rgb(0 0 0 / .1), 0 1px 2px -1px rgb(0 0 0 / .1);--shadow-m: 0 4px 6px -1px rgb(0 0 0 / .1), 0 2px 4px -2px rgb(0 0 0 / .1);--shadow-l: 0 10px 15px -3px rgb(0 0 0 / .1), 0 4px 6px -4px rgb(0 0 0 / .1);--shadow-xl: 0 20px 25px -5px rgb(0 0 0 / .1), 0 8px 10px -6px rgb(0 0 0 / .1);--shadow-2xl: 0 25px 50px -12px rgb(0 0 0 / .25);--size-xs: 1.25rem;--size-s: 1.5rem;--size-m: 1.75rem;--size-l: 2rem;--size-xl: 2.25rem;--space-25: 2px;--space-50: 4px;--space-75: 6px;--space-100: 8px;--space-150: 12px;--space-200: 16px;--space-300: 24px;--space-400: 32px;--space-450: 36px;--space-500: 40px;--space-600: 48px;--space-700: 56px;--space-800: 64px;--space-900: 72px;--radius-1: .1875rem;--radius-2: .375rem;--radius-3: .75rem}:host{--lumo-font-family: var(--font-family);--lumo-font-size-xs: var(--font-size-1);--lumo-font-size-s: var(--font-size-2);--lumo-font-size-l: var(--font-size-4);--lumo-font-size-xl: var(--font-size-5);--lumo-font-size-xxl: var(--font-size-6);--lumo-font-size-xxxl: var(--font-size-7);--lumo-line-height-s: var(--line-height-2);--lumo-line-height-m: var(--line-height-3);--lumo-line-height-l: var(--line-height-4);--lumo-border-radius-s: var(--radius-1);--lumo-border-radius-m: var(--radius-2);--lumo-border-radius-l: var(--radius-3);--lumo-base-color: var(--surface-0);--lumo-header-text-color: var(--color-high-contrast);--lumo-tertiary-text-color: var(--color);--lumo-primary-text-color: var(--color-high-contrast);--lumo-primary-color: var(--color-high-contrast);--lumo-primary-color-50pct: var(--color-accent);--lumo-primary-contrast-color: var(--lumo-secondary-text-color);--lumo-space-xs: var(--space-50);--lumo-space-s: var(--space-100);--lumo-space-m: var(--space-200);--lumo-space-l: var(--space-300);--lumo-space-xl: var(--space-500);--lumo-icon-size-xs: var(--font-size-1);--lumo-icon-size-s: var(--font-size-2);--lumo-icon-size-m: var(--font-size-3);--lumo-icon-size-l: var(--font-size-4);--lumo-icon-size-xl: var(--font-size-5);--vaadin-focus-ring-color: var(--focus-color);--vaadin-focus-ring-width: var(--focus-size);--lumo-font-size-m: var(--font-size-1);--lumo-body-text-color: var(--body-text-color);--lumo-secondary-text-color: var(--secondary-text-color);--lumo-error-text-color: var(--error-color);--lumo-size-m: var(--size-m)}:host{color-scheme:light;--surface-0: hsl(var(--gray-h) var(--gray-s) 90% / .8);--surface-1: hsl(var(--gray-h) var(--gray-s) 95% / .8);--surface-2: hsl(var(--gray-h) var(--gray-s) 100% / .8);--surface-background: linear-gradient( hsl(var(--gray-h) var(--gray-s) 95% / .7), hsl(var(--gray-h) var(--gray-s) 95% / .65) );--surface-glow: radial-gradient(circle at 30% 0%, hsl(var(--gray-h) var(--gray-s) 98% / .7), transparent 50%);--surface-border-glow: radial-gradient(at 50% 50%, hsl(var(--purple-h) 90% 90% / .8) 0, transparent 50%);--surface: var(--surface-glow) no-repeat border-box, var(--surface-background) no-repeat padding-box, hsl(var(--gray-h) var(--gray-s) 98% / .2);--surface-with-border-glow: var(--surface-glow) no-repeat border-box, linear-gradient(var(--background-color), var(--background-color)) no-repeat padding-box, var(--surface-border-glow) no-repeat border-box 0 0 / var(--glow-size, 600px) var(--glow-size, 600px);--surface-border-color: hsl(var(--gray-h) var(--gray-s) 100% / .7);--surface-backdrop-filter: blur(10px);--surface-box-shadow-1: 0 0 0 .5px hsl(var(--gray-h) var(--gray-s) 5% / .15), 0 6px 12px -1px hsl(var(--shadow-hsl) / .3);--surface-box-shadow-2: 0 0 0 .5px hsl(var(--gray-h) var(--gray-s) 5% / .15), 0 24px 40px -4px hsl(var(--shadow-hsl) / .4);--background-button: linear-gradient( hsl(var(--gray-h) var(--gray-s) 98% / .4), hsl(var(--gray-h) var(--gray-s) 90% / .2) );--background-button-active: hsl(var(--gray-h) var(--gray-s) 80% / .2);--color: var(--gray-500);--color-high-contrast: var(--gray-900);--color-accent: var(--purple-700);--color-danger: var(--red-700);--border-color: var(--gray-150);--border-color-high-contrast: var(--gray-300);--border-color-button: var(--gray-350);--border-color-popover: hsl(var(--gray-hsl) / .08);--border-color-dialog: hsl(var(--gray-hsl) / .08);--accent-color: var(--purple-600);--selection-color: hsl(var(--blue-hsl));--shadow-hsl: var(--gray-h) var(--gray-s) 20%;--lumo-contrast-5pct: var(--gray-100);--lumo-contrast-10pct: var(--gray-200);--lumo-contrast-60pct: var(--gray-400);--lumo-contrast-80pct: var(--gray-600);--lumo-contrast-90pct: var(--gray-800);--card-bg: rgba(255, 255, 255, .5);--card-hover-bg: rgba(255, 255, 255, .65);--card-open-bg: rgba(255, 255, 255, .8);--card-border: 1px solid rgba(0, 50, 100, .15);--card-open-shadow: 0px 1px 4px -1px rgba(28, 52, 84, .26);--card-section-border: var(--card-border);--card-field-bg: var(--lumo-contrast-5pct);--indicator-border: white}:host(.dark){color-scheme:dark;--surface-0: hsl(var(--gray-h) var(--gray-s) 10% / .85);--surface-1: hsl(var(--gray-h) var(--gray-s) 14% / .85);--surface-2: hsl(var(--gray-h) var(--gray-s) 18% / .85);--surface-background: linear-gradient( hsl(var(--gray-h) var(--gray-s) 8% / .65), hsl(var(--gray-h) var(--gray-s) 8% / .7) );--surface-glow: radial-gradient( circle at 30% 0%, hsl(var(--gray-h) calc(var(--gray-s) * 2) 90% / .12), transparent 50% );--surface: var(--surface-glow) no-repeat border-box, var(--surface-background) no-repeat padding-box, hsl(var(--gray-h) var(--gray-s) 20% / .4);--surface-border-glow: hsl(var(--gray-h) var(--gray-s) 20% / .4) radial-gradient(at 50% 50%, hsl(250 40% 80% / .4) 0, transparent 50%);--surface-border-color: hsl(var(--gray-h) var(--gray-s) 50% / .2);--surface-box-shadow-1: 0 0 0 .5px hsl(var(--purple-h) 40% 5% / .4), 0 6px 12px -1px hsl(var(--shadow-hsl) / .4);--surface-box-shadow-2: 0 0 0 .5px hsl(var(--purple-h) 40% 5% / .4), 0 24px 40px -4px hsl(var(--shadow-hsl) / .5);--color: var(--gray-650);--background-button: linear-gradient( hsl(var(--gray-h) calc(var(--gray-s) * 2) 80% / .1), hsl(var(--gray-h) calc(var(--gray-s) * 2) 80% / 0) );--background-button-active: hsl(var(--gray-h) var(--gray-s) 10% / .1);--border-color-popover: hsl(var(--gray-h) var(--gray-s) 90% / .1);--border-color-dialog: hsl(var(--gray-h) var(--gray-s) 90% / .1);--shadow-hsl: 0 0% 0%;--lumo-disabled-text-color: var(--lumo-contrast-60pct);--card-bg: rgba(255, 255, 255, .05);--card-hover-bg: rgba(255, 255, 255, .065);--card-open-bg: rgba(255, 255, 255, .1);--card-border: 1px solid rgba(255, 255, 255, .11);--card-open-shadow: 0px 1px 4px -1px rgba(0, 0, 0, .26);--card-section-border: var(--card-border);--card-field-bg: var(--lumo-contrast-10pct);--indicator-border: var(--lumo-base-color)}', Jc = ".items-baseline{align-items:baseline}.items-center{align-items:center}.items-end{align-items:end}.items-start{align-items:start}.border-b{border-bottom:1px var(--border-style, solid) var(--border-color, inherit)}.border-e-0{border-inline-end:none}.border-s-0{border-inline-start:none}.border-t-0{border-top:none}.border-white\\/10{--border-color: rgba(255, 255, 255, .1)}.rounded-full{border-radius:9999px}.text-blue{color:var(--blue-color)}.text-blue-violet{background-clip:text;background-image:linear-gradient(90deg,var(--blue-color),var(--violet-color));color:transparent}.text-inherit{color:inherit}.text-white,.hover\\:text-white:hover{color:#fff}.contents{display:contents}.flex{display:flex}.inline-flex{display:inline-flex}.flex-1{flex:1}.flex-col{flex-direction:column}.text-1{font-size:var(--font-size-1);line-height:var(--line-height-1)}.font-normal{font-weight:var(--font-weight-normal)}.font-medium{font-weight:var(--font-weight-medium)}.font-semibold{font-weight:var(--font-weight-semibold)}.font-bold{font-weight:var(--font-weight-bold)}.gap-25{gap:var(--space-25)}.gap-50{gap:var(--space-50)}.gap-75{gap:var(--space-75)}.gap-100{gap:var(--space-100)}.gap-150{gap:var(--space-150)}.gap-200{gap:var(--space-200)}.gap-300{gap:var(--space-300)}.gap-400{gap:var(--space-400)}.icon-s svg{height:var(--icon-size-s);width:var(--icon-size-s)}.justify-center{justify-content:center}.justify-end{justify-content:end}.list-none{list-style-type:none}.m-0{margin:0}.m-25{margin:var(--space-25)}.m-50{margin:var(--space-50)}.m-75{margin:var(--space-75)}.m-100{margin:var(--space-100)}.m-150{margin:var(--space-150)}.m-200{margin:var(--space-200)}.m-300{margin:var(--space-300)}.m-400{margin:var(--space-400)}.mb-0{margin-bottom:0}.mb-25{margin-bottom:var(--space-25)}.mb-50{margin-bottom:var(--space-50)}.mb-75{margin-bottom:var(--space-75)}.mb-100{margin-bottom:var(--space-100)}.mb-150{margin-bottom:var(--space-150)}.mb-200{margin-bottom:var(--space-200)}.mb-300{margin-bottom:var(--space-300)}.mb-400{margin-bottom:var(--space-400)}.me-25{margin-inline-end:var(--space-25)}.me-50{margin-inline-end:var(--space-50)}.me-75{margin-inline-end:var(--space-75)}.me-100{margin-inline-end:var(--space-100)}.me-150{margin-inline-end:var(--space-150)}.me-200{margin-inline-end:var(--space-200)}.me-300{margin-inline-end:var(--space-300)}.me-400{margin-inline-end:var(--space-400)}.-me-25{margin-inline-end:calc(var(--space-25) * -1)}.-me-50{margin-inline-end:calc(var(--space-50) * -1)}.-me-75{margin-inline-end:calc(var(--space-75) * -1)}.-me-100{margin-inline-end:calc(var(--space-100) * -1)}.-me-150{margin-inline-end:calc(var(--space-150) * -1)}.-me-200{margin-inline-end:calc(var(--space-200) * -1)}.-me-300{margin-inline-end:calc(var(--space-300) * -1)}.-me-400{margin-inline-end:calc(var(--space-400) * -1)}.ms-25{margin-inline-start:var(--space-25)}.ms-50{margin-inline-start:var(--space-50)}.ms-75{margin-inline-start:var(--space-75)}.ms-100{margin-inline-start:var(--space-100)}.ms-150{margin-inline-start:var(--space-150)}.ms-200{margin-inline-start:var(--space-200)}.ms-300{margin-inline-start:var(--space-300)}.ms-400{margin-inline-start:var(--space-400)}.-ms-25{margin-inline-start:calc(var(--space-25) / -1)}.-ms-50{margin-inline-start:calc(var(--space-50) / -1)}.-ms-75{margin-inline-start:calc(var(--space-75) / -1)}.-ms-100{margin-inline-start:calc(var(--space-100) / -1)}.-ms-150{margin-inline-start:calc(var(--space-150) / -1)}.-ms-200{margin-inline-start:calc(var(--space-200) / -1)}.-ms-300{margin-inline-start:var(--space-300)}.-ms-400{margin-inline-start:var(--space-400)}.mt-25{margin-top:var(--space-25)}.mt-50{margin-top:var(--space-50)}.mt-75{margin-top:var(--space-75)}.mt-100{margin-top:var(--space-100)}.mt-150{margin-top:var(--space-150)}.mt-200{margin-top:var(--space-200)}.mt-300{margin-top:var(--space-300)}.mt-400{margin-top:var(--space-400)}.-mt-25{margin-top:calc(var(--space-25) * -1)}.-mt-50{margin-top:calc(var(--space-50) * -1)}.-mt-75{margin-top:calc(var(--space-75) * -1)}.-mt-100{margin-top:calc(var(--space-100) * -1)}.-mt-150{margin-top:calc(var(--space-150) * -1)}.-mt-200{margin-top:calc(var(--space-200) * -1)}.-mt-300{margin-top:calc(var(--space-300) * -1)}.-mt-400{margin-top:calc(var(--space-400) * -1)}.mx-25{margin-inline:var(--space-25)}.mx-50{margin-inline:var(--space-50)}.mx-75{margin-inline:var(--space-75)}.mx-100{margin-inline:var(--space-100)}.mx-150{margin-inline:var(--space-150)}.mx-200{margin-inline:var(--space-200)}.mx-300{margin-inline:var(--space-300)}.mx-400{margin-inline:var(--space-400)}.my-25{margin-block:var(--space-25)}.my-50{margin-block:var(--space-50)}.my-75{margin-block:var(--space-75)}.my-100{margin-block:var(--space-100)}.my-150{margin-block:var(--space-150)}.my-200{margin-block:var(--space-200)}.my-300{margin-block:var(--space-300)}.my-400{margin-block:var(--space-400)}.truncate{overflow:hidden;text-overflow:ellipsis;white-space:nowrap}.p-0{padding:0}.p-25{padding:var(--space-25)}.p-50{padding:var(--space-50)}.p-75{padding:var(--space-75)}.p-100{padding:var(--space-100)}.p-150{padding:var(--space-150)}.p-200{padding:var(--space-200)}.p-300{padding:var(--space-300)}.p-400{padding:var(--space-400)}.pb-25{padding-bottom:var(--space-25)}.pb-50{padding-bottom:var(--space-50)}.pb-75{padding-bottom:var(--space-75)}.pb-100{padding-bottom:var(--space-100)}.pb-150{padding-bottom:var(--space-150)}.pb-200{padding-bottom:var(--space-200)}.pb-300{padding-bottom:var(--space-300)}.pb-400{padding-bottom:var(--space-400)}.pe-25{padding-inline-end:var(--space-25)}.pe-50{padding-inline-end:var(--space-50)}.pe-75{padding-inline-end:var(--space-75)}.pe-100{padding-inline-end:var(--space-100)}.pe-150{padding-inline-end:var(--space-150)}.pe-200{padding-inline-end:var(--space-200)}.pe-300{padding-inline-end:var(--space-300)}.pe-400{padding-inline-end:var(--space-400)}.ps-25{padding-inline-start:var(--space-25)}.ps-50{padding-inline-start:var(--space-50)}.ps-75{padding-inline-start:var(--space-75)}.ps-100{padding-inline-start:var(--space-100)}.ps-150{padding-inline-start:var(--space-150)}.ps-200{padding-inline-start:var(--space-200)}.ps-300{padding-inline-start:var(--space-300)}.ps-400{padding-inline-start:var(--space-400)}.pt-25{padding-top:var(--space-25)}.pt-50{padding-top:var(--space-50)}.pt-75{padding-top:var(--space-75)}.pt-100{padding-top:var(--space-100)}.pt-150{padding-top:var(--space-150)}.pt-200{padding-top:var(--space-200)}.pt-300{padding-top:var(--space-300)}.pt-400{padding-top:var(--space-400)}.px-25{padding-inline:var(--space-25)}.px-50{padding-inline:var(--space-50)}.px-75{padding-inline:var(--space-75)}.px-100{padding-inline:var(--space-100)}.px-150{padding-inline:var(--space-150)}.px-200{padding-inline:var(--space-200)}.px-300{padding-inline:var(--space-300)}.px-400{padding-inline:var(--space-400)}.py-25{padding-block:var(--space-25)}.py-50{padding-block:var(--space-50)}.py-75{padding-block:var(--space-75)}.py-100{padding-block:var(--space-100)}.py-150{padding-block:var(--space-150)}.py-200{padding-block:var(--space-200)}.py-300{padding-block:var(--space-300)}.py-400{padding-block:var(--space-400)}.absolute{position:absolute}.relative{position:relative}.end-0{inset-inline-end:0}.start-0{inset-inline-start:0}.top-0{top:0}.h-m{height:var(--size-m)}.h-75{height:var(--space-75)}.h-900{height:var(--space-900)}.size-m{height:var(--size-m);width:var(--size-m)}.w-75{width:var(--space-75)}.rotate-90{transform:rotate(90deg)}";
var qu = typeof globalThis < "u" ? globalThis : typeof window < "u" ? window : typeof global < "u" ? global : typeof self < "u" ? self : {};
function Xc(e) {
  return e && e.__esModule && Object.prototype.hasOwnProperty.call(e, "default") ? e.default : e;
}
function Ku(e) {
  if (Object.prototype.hasOwnProperty.call(e, "__esModule")) return e;
  var t = e.default;
  if (typeof t == "function") {
    var n = function r() {
      return this instanceof r ? Reflect.construct(t, arguments, this.constructor) : t.apply(this, arguments);
    };
    n.prototype = t.prototype;
  } else n = {};
  return Object.defineProperty(n, "__esModule", { value: !0 }), Object.keys(e).forEach(function(r) {
    var i = Object.getOwnPropertyDescriptor(e, r);
    Object.defineProperty(n, r, i.get ? i : {
      enumerable: !0,
      get: function() {
        return e[r];
      }
    });
  }), n;
}
var Rt = { exports: {} }, ti;
function Zc() {
  if (ti) return Rt.exports;
  ti = 1;
  function e(t, n = 100, r = {}) {
    if (typeof t != "function")
      throw new TypeError(`Expected the first parameter to be a function, got \`${typeof t}\`.`);
    if (n < 0)
      throw new RangeError("`wait` must not be negative.");
    const { immediate: i } = typeof r == "boolean" ? { immediate: r } : r;
    let o, a, s, l, c;
    function u() {
      const h = o, b = a;
      return o = void 0, a = void 0, c = t.apply(h, b), c;
    }
    function d() {
      const h = Date.now() - l;
      h < n && h >= 0 ? s = setTimeout(d, n - h) : (s = void 0, i || (c = u()));
    }
    const v = function(...h) {
      if (o && this !== o && Object.getPrototypeOf(this) === Object.getPrototypeOf(o))
        throw new Error("Debounced method called with different contexts of the same prototype.");
      o = this, a = h, l = Date.now();
      const b = i && !s;
      return s || (s = setTimeout(d, n)), b && (c = u()), c;
    };
    return Object.defineProperty(v, "isPending", {
      get() {
        return s !== void 0;
      }
    }), v.clear = () => {
      s && (clearTimeout(s), s = void 0);
    }, v.flush = () => {
      s && v.trigger();
    }, v.trigger = () => {
      c = u(), v.clear();
    }, v;
  }
  return Rt.exports.debounce = e, Rt.exports = e, Rt.exports;
}
var Qc = /* @__PURE__ */ Zc();
const eu = /* @__PURE__ */ Xc(Qc);
class tu {
  constructor() {
    this.documentActive = !0, this.addListeners = () => {
      window.addEventListener("pageshow", this.handleWindowVisibilityChange), window.addEventListener("pagehide", this.handleWindowVisibilityChange), window.addEventListener("focus", this.handleWindowFocusChange), window.addEventListener("blur", this.handleWindowFocusChange), document.addEventListener("visibilitychange", this.handleDocumentVisibilityChange);
    }, this.removeListeners = () => {
      window.removeEventListener("pageshow", this.handleWindowVisibilityChange), window.removeEventListener("pagehide", this.handleWindowVisibilityChange), window.removeEventListener("focus", this.handleWindowFocusChange), window.removeEventListener("blur", this.handleWindowFocusChange), document.removeEventListener("visibilitychange", this.handleDocumentVisibilityChange);
    }, this.handleWindowVisibilityChange = (t) => {
      t.type === "pageshow" ? this.dispatch(!0) : this.dispatch(!1);
    }, this.handleWindowFocusChange = (t) => {
      t.type === "focus" ? this.dispatch(!0) : this.dispatch(!1);
    }, this.handleDocumentVisibilityChange = () => {
      this.dispatch(!document.hidden);
    }, this.dispatch = (t) => {
      if (t !== this.documentActive) {
        const n = window.Vaadin.copilot.eventbus;
        this.documentActive = t, n.emit("document-activation-change", { active: this.documentActive });
      }
    };
  }
  copilotActivated() {
    this.addListeners();
  }
  copilotDeactivated() {
    this.removeListeners();
  }
}
const ni = new tu(), nu = "copilot-development-setup-user-guide";
function Wu() {
  wt("use-dev-workflow-guide"), se.updatePanel(nu, { floating: !0 });
}
function zo() {
  const e = g.jdkInfo;
  return e ? e.jrebel ? "success" : e.hotswapAgentFound ? !e.hotswapVersionOk || !e.runningWithExtendClassDef || !e.runningWitHotswap || !e.runningInJavaDebugMode ? "error" : "success" : "warning" : null;
}
function Gu() {
  const e = g.jdkInfo;
  return !e || zo() !== "success" ? "none" : e.jrebel ? "jrebel" : e.runningWitHotswap ? "hotswap" : "none";
}
function ru() {
  return g.idePluginState?.ide === "eclipse" ? "unsupported" : g.idePluginState !== void 0 && !g.idePluginState.active ? "warning" : "success";
}
function Yu() {
  if (!g.jdkInfo)
    return { status: "success" };
  const e = zo(), t = ru();
  return e === "warning" ? t === "warning" ? { status: "warning", message: "IDE Plugin, Hotswap" } : { status: "warning", message: "Hotswap is not enabled" } : t === "warning" ? { status: "warning", message: "IDE Plugin is not active" } : e === "error" ? { status: "error", message: "Hotswap is partially enabled" } : { status: "success" };
}
function iu() {
  te(`${Re}get-dev-setup-info`, {}), window.Vaadin.copilot.eventbus.on("copilot-get-dev-setup-info-response", (e) => {
    if (e.detail.content) {
      const t = JSON.parse(e.detail.content);
      g.setIdePluginState(t.ideInfo), g.setJdkInfo(t.jdkInfo);
    }
  });
}
const ut = /* @__PURE__ */ new WeakMap();
class ou {
  constructor() {
    this.root = null, this.nodeUuidNodeMapFlat = /* @__PURE__ */ new Map(), this.aborted = !1, this._hasFlowComponent = !1, this.flowNodesInSource = {}, this.flowCustomComponentData = {};
  }
  async init() {
    const t = Xs();
    t && await this.addToTree(t) && (await this.addOverlayContentToTreeIfExists("vaadin-popover-overlay"), await this.addOverlayContentToTreeIfExists("vaadin-dialog-overlay"));
  }
  getChildren(t) {
    return this.nodeUuidNodeMapFlat.get(t)?.children ?? [];
  }
  get allNodesFlat() {
    return Array.from(this.nodeUuidNodeMapFlat.values());
  }
  getNodeOfElement(t) {
    if (t)
      return this.allNodesFlat.find((n) => n.element === t);
  }
  /**
   * Handles route containers that should not be present in the tree. When this returns <code>true</code>, it means that given node is a route container so adding it to tree should be skipped
   *
   * @param node Node to check whether it is a route container or not
   * @param parentNode Parent of the given {@link node}
   */
  async handleRouteContainers(t, n) {
    const r = Pr(t);
    if (!r && ol(t)) {
      const i = en(t);
      if (i && i.nextElementSibling)
        return await this.addToTree(i.nextElementSibling, n), !0;
    }
    if (r && t.localName === "react-router-outlet") {
      for (const i of Array.from(t.children)) {
        const o = Qt(i);
        o && await this.addToTree(o, n);
      }
      return !0;
    }
    return !1;
  }
  includeReactNode(t) {
    return ft(t) === "PreconfiguredAuthProvider" || ft(t) === "RouterProvider" ? !1 : Cr(t) || tl(t);
  }
  async includeFlowNode(t) {
    return al(t) || On(t)?.hiddenByServer ? !1 : this.isInitializedInProjectSources(t);
  }
  async isInitializedInProjectSources(t) {
    const n = On(t);
    if (!n)
      return !1;
    const { nodeId: r, uiId: i } = n;
    if (!this.flowNodesInSource[i]) {
      const o = await vn("copilot-get-component-source-info", { uiId: i }, (a) => a.data);
      o.error && le("Failed to get component source info", o.error), this.flowCustomComponentData[i] = o.customComponentResponse, this.flowNodesInSource[i] = new Set(o.nodeIdsInProject);
    }
    return this.flowNodesInSource[i].has(r);
  }
  /**
   * Adds the given element into the tree and returns the result when added.
   * <p>
   *  It recursively travels through the children of given node. This method is called for each child ,but the result of adding a child is swallowed
   * </p>
   * @param node Node to add to tree
   * @param parentNode Parent of the node, might be null if it is the root element
   */
  async addToTree(t, n) {
    if (this.isAborted())
      return !1;
    const r = await this.handleRouteContainers(t, n);
    if (r)
      return r;
    const i = Pr(t);
    let o;
    if (!i)
      this.includeReactNode(t) && (o = this.generateNodeFromFiber(t, n));
    else if (await this.includeFlowNode(t)) {
      const l = this.generateNodeFromFlow(t, n);
      if (!l)
        return !1;
      this._hasFlowComponent = !0, o = l;
    }
    if (n)
      o && (o.parent = n, n.children || (n.children = []), n.children.push(o));
    else {
      if (!o)
        return !(t instanceof Element) && no(t) ? (No({
          type: Ve.WARNING,
          message: "Copilot is partly usable",
          details: `${ft(t)} should be a function component to make Copilot work properly`,
          dismissId: "react_route_component_is_class"
        }), !1) : (le("Unable to add node", new Error("Tree root node is undefined")), !1);
      this.root = o;
    }
    o && this.nodeUuidNodeMapFlat.set(o.uuid, o);
    const a = o ?? n, s = i ? Array.from(t.children) : Zs(t);
    for (const l of s)
      await this.addToTree(l, a);
    return o !== void 0;
  }
  generateNodeFromFiber(t, n) {
    const r = Cr(t) ? en(t) : void 0, i = n?.children.length ?? 0;
    return {
      node: t,
      parent: n,
      element: r,
      depth: n && n.depth + 1 || 0,
      children: [],
      siblingIndex: i,
      isFlowComponent: !1,
      isReactComponent: !0,
      isLitElement: !1,
      zeroSize: r ? kr(r) : void 0,
      get uuid() {
        if (ut.has(t))
          return ut.get(t);
        if (t.alternate && ut.has(t.alternate))
          return ut.get(t.alternate);
        const a = jo();
        return ut.set(t, a), a;
      },
      get name() {
        return $r(ft(t));
      },
      get identifier() {
        return Dr(r);
      },
      get nameAndIdentifier() {
        return ii(this.name, this.identifier);
      },
      get previousSibling() {
        if (i !== 0)
          return n?.children[i - 1];
      },
      get nextSibling() {
        if (!(n === void 0 || i === n.children.length - 1))
          return n.children[i + 1];
      },
      get path() {
        return ri(this);
      }
    };
  }
  generateNodeFromFlow(t, n) {
    const r = On(t);
    if (!r)
      return;
    const i = n?.children.length ?? 0, o = this.flowCustomComponentData;
    return {
      node: r,
      parent: n,
      element: t,
      depth: n && n.depth + 1 || 0,
      children: [],
      siblingIndex: i,
      get uuid() {
        return `${r.uiId}#${r.nodeId}`;
      },
      isFlowComponent: !0,
      isReactComponent: !1,
      get isLitElement() {
        return this.element instanceof Fe;
      },
      zeroSize: t ? kr(t) : void 0,
      get name() {
        return il(r) ?? $r(r.element.localName);
      },
      get identifier() {
        return Dr(t);
      },
      get nameAndIdentifier() {
        return ii(this.name, this.identifier);
      },
      get previousSibling() {
        if (i !== 0)
          return n?.children[i - 1];
      },
      get nextSibling() {
        if (!(n === void 0 || i === n.children.length - 1))
          return n.children[i + 1];
      },
      get path() {
        return ri(this);
      },
      get customComponentData() {
        if (o[r.uiId])
          return o[r.uiId].allComponentsInfoForCustomComponentSupport[r.nodeId];
      }
    };
  }
  async addOverlayContentToTreeIfExists(t) {
    const n = document.body.querySelector(t);
    if (!n)
      return;
    const r = n.owner;
    if (!r)
      return;
    let i = !0;
    if (!this.getNodeOfElement(r)) {
      const o = $e(Qt(r));
      i = await this.addToTree(o ?? r, this.root);
    }
    if (i)
      for (const o of Array.from(n.children))
        await this.addToTree(o, this.getNodeOfElement(r));
  }
  hasFlowComponents() {
    return this._hasFlowComponent;
  }
  findNodeByUuid(t) {
    if (t)
      return this.nodeUuidNodeMapFlat.get(t);
  }
  getElementByNodeUuid(t) {
    return this.findNodeByUuid(t)?.element;
  }
  findByTreePath(t) {
    if (t)
      return this.allNodesFlat.find((n) => n.path === t);
  }
  isAborted() {
    return this.aborted;
  }
  abort() {
    this.aborted = !0;
  }
}
function ri(e) {
  if (!e.parent)
    return e.name;
  let t = 0;
  for (let n = 0; n < e.siblingIndex + 1; n++)
    e.parent.children[n].name === e.name && t++;
  return `${e.parent.path} > ${e.name}[${t}]`;
}
function ii(e, t) {
  return t ? `${e} "${t}"` : e;
}
let Be = null;
const au = async () => {
  Be && Be.abort();
  const e = new ou();
  Be = e, await e.init(), Be = null, e.isAborted() || (window.Vaadin.copilot.tree.currentTree = e);
}, Ju = () => {
  Be && Be.abort();
};
function su() {
  const e = window.navigator.userAgent;
  return e.indexOf("Windows") !== -1 ? "Windows" : e.indexOf("Mac") !== -1 ? "Mac" : e.indexOf("Linux") !== -1 ? "Linux" : null;
}
function lu() {
  return su() === "Mac";
}
function cu() {
  return lu() ? "⌘" : "Ctrl";
}
let Pn = !1, dt = 0;
const qn = (e) => {
  if (nt.isActivationShortcut())
    if (e.key === "Shift" && !e.ctrlKey && !e.altKey && !e.metaKey)
      Pn = !0;
    else if (Pn && e.shiftKey && (e.key === "Control" || e.key === "Meta")) {
      if (dt++, dt === 2)
        return g.toggleActive("shortcut"), dt = 0, !0;
      setTimeout(() => {
        dt = 0;
      }, 500);
    } else
      Pn = !1, dt = 0;
  return !1;
};
function oi(e) {
  if ((e.ctrlKey || e.metaKey) && e.key === "c") {
    const n = document.querySelector("copilot-main")?.shadowRoot?.getSelection();
    if (n && n.rangeCount === 1) {
      const i = n.getRangeAt(0).commonAncestorContainer;
      return Ge(i);
    }
  }
  return !1;
}
function uu(e) {
  const t = zn(e, "vaadin-context-menu-overlay");
  if (!t)
    return !1;
  const n = t.owner;
  return n ? !!zn(n, "copilot-component-overlay") : !1;
}
function du() {
  return g.idePluginState?.supportedActions?.find((e) => e === "undo");
}
const ai = (e) => {
  if (!g.active)
    return;
  if (qn(e)) {
    e.stopPropagation();
    return;
  }
  const t = _l();
  if (!t)
    return;
  const n = uu(t);
  if (!(t.localName === "copilot-main") && !n) {
    e.stopPropagation();
    return;
  }
  let i = !0, o = !1;
  oi(e) ? i = !1 : e.key === "Escape" ? g.loginCheckActive ? g.setLoginCheckActive(!1) : y.emit("close-drawers", {}) : fu(e) ? (y.emit("delete-selected", {}), o = !0) : (e.ctrlKey || e.metaKey) && e.key === "d" ? (y.emit("duplicate-selected", {}), o = !0) : (e.ctrlKey || e.metaKey) && e.key === "b" ? (y.emit("show-selected-in-ide", { attach: e.shiftKey }), o = !0) : (e.ctrlKey || e.metaKey) && e.key === "z" && du() ? (y.emit("undoRedo", { undo: !e.shiftKey }), o = !0) : oi(e) || y.emit("keyboard-event", { event: e }), i && e.stopPropagation(), o && e.preventDefault();
}, fu = (e) => (e.key === "Backspace" || e.key === "Delete") && !e.shiftKey && !e.ctrlKey && !e.altKey && !e.metaKey, ne = cu(), jt = "⇧", Xu = {
  toggleCopilot: `<kbd>${jt}</kbd> + <kbd>${ne}</kbd> <kbd>${ne}</kbd>`,
  toggleCommandWindow: `<kbd>${jt}</kbd> + <kbd>Space</kbd>`,
  undo: `<kbd>${ne}</kbd> + <kbd>Z</kbd>`,
  redo: `<kbd>${ne}</kbd> + <kbd>${jt}</kbd> + <kbd>Z</kbd>`,
  duplicate: `<kbd>${ne}</kbd> + <kbd>D</kbd>`,
  goToSource: `<kbd>${ne}</kbd> + <kbd>B</kbd>`,
  goToAttachSource: `<kbd>${ne}</kbd>  + <kbd>${jt}</kbd>  + <kbd>B</kbd>`,
  selectParent: "<kbd>←</kbd>",
  selectPreviousSibling: "<kbd>↑</kbd>",
  selectNextSibling: "<kbd>↓</kbd>",
  delete: "<kbd>DEL</kbd>",
  copy: `<kbd>${ne}</kbd> + <kbd>C</kbd>`,
  paste: `<kbd>${ne}</kbd> + <kbd>V</kbd>`
};
var pu = Object.getOwnPropertyDescriptor, vu = (e, t, n, r) => {
  for (var i = r > 1 ? void 0 : r ? pu(t, n) : t, o = e.length - 1, a; o >= 0; o--)
    (a = e[o]) && (i = a(i) || i);
  return i;
};
let si = class extends rc {
  constructor() {
    super(...arguments), this.removers = [], this.initialized = !1, this.active = !1, this.toggleOperationInProgressAttr = () => {
      this.toggleAttribute("operation-in-progress", g.operationWaitsHmrUpdate !== void 0);
    }, this.operationInProgressCursorUpdateDebounceFunc = eu(this.toggleOperationInProgressAttr, 500), this.overlayOutsideClickListener = (e) => {
      Ge(e.target?.owner) || (g.active || Ge(e.detail.sourceEvent.target)) && e.preventDefault();
    }, this.mouseLeaveListener = () => {
      y.emit("close-drawers", {});
    };
  }
  static get styles() {
    return [
      R(zc),
      R(Uc),
      R(Bc),
      R(Fc),
      R(Hc),
      R(qc),
      R(Wc),
      R(Kc),
      R(Gc),
      R(Yc),
      R(Jc),
      kl`
        :host {
          color: var(--body-text-color);
          contain: strict;
          cursor: var(--cursor, default);
          font: var(--font-xsmall);
          inset: 0;
          pointer-events: all;
          position: fixed;
          z-index: 9999;
        }

        :host([operation-in-progress]) {
          --cursor: wait;
          --lumo-clickable-cursor: wait;
        }

        :host(:not([active])) {
          visibility: hidden !important;
          pointer-events: none;
        }

        /* Hide floating panels when not active */

        :host(:not([active])) > copilot-section-panel-wrapper {
          display: none !important;
        }
        :host(:not([active])) > copilot-section-panel-wrapper[individual] {
          display: block !important;
          visibility: visible;
          pointer-events: all;
        }

        /* Keep activation button and menu visible */

        copilot-activation-button,
        .activation-button-menu {
          visibility: visible;
          display: flex !important;
        }

        copilot-activation-button {
          pointer-events: auto;
        }

        a {
          border-radius: var(--radius-2);
          color: var(--blue-color);
          outline-offset: calc(var(--focus-size) / -1);
        }

        a:focus {
          animation-delay: 0s, 0.15s;
          animation-duration: 0.15s, 0.45s;
          animation-name: link-focus-in, link-focus-out;
          animation-timing-function: cubic-bezier(0.2, 0, 0, 1), cubic-bezier(0.2, 0, 0, 1);
          outline: var(--focus-size) solid var(--blue-color);
        }

        :host([user-select-none]) {
          -webkit-touch-callout: none;
          -webkit-user-select: none;
          -moz-user-select: none;
          -ms-user-select: none;
          user-select: none;
        }

        /* Needed to prevent a JS error because of monkey patched '_attachOverlay'. It is some scope issue, */
        /* where 'this._placeholder.parentNode' is undefined - the scope if 'this' gets messed up at some point. */
        /* We also don't want animations on the overlays to make the feel faster, so this is fine. */
        :is(vaadin-tooltip-overlay) {
          z-index: calc(var(--copilot-notifications-container-z-index) + 10);
        }
        :is(
            vaadin-context-menu-overlay,
            vaadin-menu-bar-overlay,
            vaadin-select-overlay,
            vaadin-combo-box-overlay,
            vaadin-tooltip-overlay,
            vaadin-multi-select-combo-box-overlay
          ):is([opening], [closing]),
        :is(
            vaadin-context-menu-overlay,
            vaadin-menu-bar-overlay,
            vaadin-select-overlay,
            vaadin-combo-box-overlay,
            vaadin-tooltip-overlay,
            vaadin-multi-select-combo-box-overlay
          )::part(overlay) {
          animation: none !important;
        }

        :host(:not([active])) copilot-drawer-panel::before {
          animation: none;
        }

        /* Workaround for https://github.com/vaadin/web-components/issues/5400 */

        :host(:not([active])) .activation-button-menu .toggle-spotlight {
          display: none;
        }

        :host .alwaysVisible {
          visibility: visible !important;
        }
      `
    ];
  }
  connectedCallback() {
    super.connectedCallback(), this.init().catch((e) => le("Unable to initialize copilot", e));
  }
  async init() {
    if (this.initialized)
      return;
    await window.Vaadin.copilot._machineState.initializer.promise, await import("./copilot-global-vars-later-DPO6A8CI.js"), await import("./copilot-init-step2-DX7OJsbz.js"), dl(), ic(), this.tabIndex = 0, Vt.hostConnectedCallback(), window.addEventListener("keydown", qn), this.addEventListener("keydown", ai), y.onSend(this.handleSendEvent), this.removers.push(y.on("close-drawers", this.closeDrawers.bind(this))), this.removers.push(
      y.on("open-attention-required-drawer", this.openDrawerIfPanelRequiresAttention.bind(this))
    ), this.removers.push(
      y.on("set-pointer-events", (n) => {
        this.style.pointerEvents = n.detail.enable ? "" : "none";
      })
    ), this.addEventListener("mousemove", this.mouseMoveListener), this.addEventListener("dragover", this.dragOverListener), this.addEventListener("dragleave", this.dragLeaveListener), this.addEventListener("drop", this.dropListener), lt.addOverlayOutsideClickEvent();
    const e = window.matchMedia("(prefers-color-scheme: dark)");
    this.classList.toggle("dark", e.matches), e.addEventListener("change", (n) => {
      this.classList.toggle("dark", e.matches);
    }), this.reaction(
      () => g.spotlightActive,
      () => {
        fe.saveSpotlightActivation(g.spotlightActive), Array.from(this.shadowRoot.querySelectorAll("copilot-section-panel-wrapper")).filter((n) => n.panelInfo?.floating === !0).forEach((n) => {
          g.spotlightActive ? n.style.setProperty("display", "none") : n.style.removeProperty("display");
        });
      }
    ), this.reaction(
      () => g.active,
      () => {
        this.toggleAttribute("active", g.active), g.active ? this.activate() : this.deactivate(), fe.saveCopilotActivation(g.active);
      }
    ), this.reaction(
      () => g.activatedAtLeastOnce,
      () => {
        To(), oc();
      }
    ), this.reaction(
      () => g.sectionPanelDragging,
      () => {
        g.sectionPanelDragging && Array.from(this.shadowRoot.children).filter((r) => r.localName.endsWith("-overlay")).forEach((r) => {
          r.close && r.close();
        });
      }
    ), this.reaction(
      () => g.operationWaitsHmrUpdate,
      () => {
        g.operationWaitsHmrUpdate ? this.operationInProgressCursorUpdateDebounceFunc() : (this.operationInProgressCursorUpdateDebounceFunc.clear(), this.toggleOperationInProgressAttr());
      }
    ), this.reaction(
      () => se.panels,
      () => {
        se.panels.find((n) => n.individual) && this.requestUpdate();
      }
    ), fe.getCopilotActivation() && ao().then(() => {
      g.setActive(!0, "restore");
    }), this.removers.push(
      y.on("user-select", (n) => {
        const { allowSelection: r } = n.detail;
        this.toggleAttribute("user-select-none", !r);
      })
    ), this.removers.push(
      y.on("featureFlags", (n) => {
        const r = n.detail.features;
        g.setFeatureFlags(r);
      })
    );
    const t = new ResizeObserver(() => {
      y.emit("copilot-main-resized", {});
    });
    t.observe(this), this.removers.push(() => {
      t.disconnect();
    }), Do(), this.initialized = !0, iu();
  }
  /**
   * Called when Copilot is activated. Good place to start attach listeners etc.
   */
  async activate() {
    Vt.activate(), ni.copilotActivated(), ac(), this.openDrawerIfPanelRequiresAttention(), document.documentElement.addEventListener("mouseleave", this.mouseLeaveListener), lt.onCopilotActivation(), await au(), Po.loadPreviewConfiguration(), this.active = !0, nt.isActivationAnimation() && g.activatedFrom !== "restore" && this.getAllDrawers().forEach((e) => {
      e.setAttribute("bounce", "");
    });
  }
  /**
   * Called when Copilot is deactivated. Good place to remove listeners etc.
   */
  deactivate() {
    this.getAllDrawers().forEach((e) => {
      e.removeAttribute("bounce");
    }), this.closeDrawers(), Vt.deactivate(), ni.copilotDeactivated(), document.documentElement.removeEventListener("mouseleave", this.mouseLeaveListener), lt.onCopilotDeactivation(), this.active = !1;
  }
  getAllDrawers() {
    return Array.from(this.shadowRoot.querySelectorAll("copilot-drawer-panel"));
  }
  disconnectedCallback() {
    super.disconnectedCallback(), Vt.hostDisconnectedCallback(), window.removeEventListener("keydown", qn), this.removeEventListener("keydown", ai), y.offSend(this.handleSendEvent), this.removers.forEach((e) => e()), this.removeEventListener("mousemove", this.mouseMoveListener), this.removeEventListener("dragover", this.dragOverListener), this.removeEventListener("dragleave", this.dragLeaveListener), this.removeEventListener("drop", this.dropListener), lt.removeOverlayOutsideClickEvent(), document.documentElement.removeEventListener("vaadin-overlay-outside-click", this.overlayOutsideClickListener);
  }
  handleSendEvent(e) {
    const t = e.detail.command, n = e.detail.data;
    te(t, n);
  }
  /**
   * Opens the attention required drawer if there is any.
   */
  openDrawerIfPanelRequiresAttention() {
    const e = se.getAttentionRequiredPanelConfiguration();
    if (!e)
      return;
    const t = e.panel;
    if (!t || e.floating)
      return;
    const n = this.shadowRoot.querySelector(`copilot-drawer-panel[position="${t}"]`);
    n.opened = !0;
  }
  render() {
    return pe`
      <copilot-activation-button
        @activation-btn-clicked="${() => {
      g.toggleActive("button"), g.setLoginCheckActive(!1);
    }}"
        @spotlight-activation-changed="${(e) => {
      g.setSpotlightActive(e.detail);
    }}"
        .spotlightOn="${g.spotlightActive}">
      </copilot-activation-button>
      <copilot-component-selector></copilot-component-selector>
      <copilot-label-editor-container></copilot-label-editor-container>
      <copilot-info-tooltip></copilot-info-tooltip>
      ${this.renderDrawer("left")} ${this.renderDrawer("right")} ${this.renderDrawer("bottom")} ${Tc()}
      ${this.renderSpotlight()}
      <copilot-login-check ?active=${g.loginCheckActive && g.active}></copilot-login-check>
      <copilot-ai-usage-confirmation-dialog></copilot-ai-usage-confirmation-dialog>
      <copilot-notifications-container></copilot-notifications-container>
      <copilot-report-exception-dialog></copilot-report-exception-dialog>
    `;
  }
  renderSpotlight() {
    return pe`
      <copilot-spotlight ?active=${g.spotlightActive && g.active}></copilot-spotlight>
    `;
  }
  renderDrawer(e) {
    return pe` <copilot-drawer-panel position=${e}> ${kc(e)} </copilot-drawer-panel>`;
  }
  /**
   * Closes the open drawers if any opened unless an overlay is opened from drawer.
   */
  closeDrawers() {
    const e = this.getAllDrawers();
    if (!Array.from(e).some((o) => o.opened))
      return;
    const n = Array.from(this.shadowRoot.children).find(
      (o) => o.localName.endsWith("overlay")
    ), r = n && lt.getOwner(n);
    if (!r) {
      e.forEach((o) => {
        o.opened = !1;
      });
      return;
    }
    const i = zn(r, "copilot-drawer-panel");
    if (!i) {
      e.forEach((o) => {
        o.opened = !1;
      });
      return;
    }
    Array.from(e).filter((o) => o.position !== i.position).forEach((o) => {
      o.opened = !1;
    });
  }
  updated(e) {
    super.updated(e), this.attachActivationButtonToBody(), Lc();
  }
  attachActivationButtonToBody() {
    const e = document.body.querySelectorAll("copilot-activation-button");
    e.length > 1 && e[0].remove();
  }
  mouseMoveListener(e) {
    e.composedPath().find((t) => t.localName === `${Re}drawer-panel`) || this.closeDrawers();
  }
  dragOverListener(e) {
    this.mouseMoveListener(e), e.dataTransfer && (e.dataTransfer.dropEffect = "none"), e.preventDefault(), y.emit("drag-and-drop-in-progress", {});
  }
  dragLeaveListener(e) {
    Al(e) && y.emit("end-drag-drop", {});
  }
  dropListener(e) {
    e.preventDefault(), y.emit("end-drag-drop", {});
  }
};
si = vu([
  Dl("copilot-main")
], si);
const hu = window.Vaadin, gu = {
  init(e) {
    ro(
      () => window.Vaadin.devTools,
      (t) => {
        const n = t.handleFrontendMessage;
        t.handleFrontendMessage = (r) => {
          Mc(r) || n.call(t, r);
        };
      }
    );
  }
};
hu.devToolsPlugins.push(gu);
function Zu(e) {
  mu().unshift(e);
}
function mu() {
  return window.Vaadin.copilot.figmaImporters ??= [], window.Vaadin.copilot.figmaImporters;
}
export {
  Fu as $,
  zc as A,
  kl as B,
  fe as C,
  yu as D,
  O as E,
  pe as F,
  Fe as G,
  Mu as H,
  nu as I,
  zo as J,
  bu as K,
  Gu as L,
  Ve as M,
  wu as N,
  Lu as O,
  Re as P,
  Yu as Q,
  Po as R,
  nt as S,
  $u as T,
  No as U,
  Eu as V,
  Wu as W,
  Xu as X,
  ul as Y,
  Bu as Z,
  Uc as _,
  Xc as a,
  lc as a0,
  Au as a1,
  wt as a2,
  _u as a3,
  Bc as a4,
  Jc as a5,
  Nu as a6,
  Ao as a7,
  au as a8,
  Ju as a9,
  Oo as aa,
  Uu as ab,
  wo as ac,
  ec as ad,
  Eo as ae,
  oo as af,
  Su as ag,
  ko as ah,
  Hn as ai,
  sl as aj,
  zu as ak,
  ru as al,
  Co as am,
  So as an,
  To as ao,
  ho as ap,
  Bn as aq,
  Vu as ar,
  Zu as as,
  y as b,
  qu as c,
  Zt as d,
  Ws as e,
  xu as f,
  Ku as g,
  Cu as h,
  Ou as i,
  g as j,
  vn as k,
  le as l,
  Ze as m,
  Du as n,
  x as o,
  hn as p,
  Hu as q,
  ou as r,
  te as s,
  Pu as t,
  Dl as u,
  rc as v,
  eu as w,
  se as x,
  pl as y,
  R as z
};
