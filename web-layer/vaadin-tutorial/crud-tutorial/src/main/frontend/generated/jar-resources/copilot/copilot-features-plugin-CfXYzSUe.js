import { j as d, F as r, a2 as c, am as g, af as f, an as u, U as h, M as m, ao as v, u as b } from "./copilot-C3Q8RayU.js";
import { B as $ } from "./base-panel-Dr4zQ6S6.js";
import { i as w } from "./icons-hMCj4mhz.js";
const x = "copilot-features-panel{padding:var(--space-100);font:var(--font-xsmall);display:grid;grid-template-columns:auto 1fr;gap:var(--space-50);height:auto}copilot-features-panel a{display:flex;align-items:center;gap:var(--space-50);white-space:nowrap}copilot-features-panel a svg{height:12px;width:12px;min-height:12px;min-width:12px}";
var F = Object.getOwnPropertyDescriptor, y = (e, a, o, s) => {
  for (var t = s > 1 ? void 0 : s ? F(a, o) : a, l = e.length - 1, i; l >= 0; l--)
    (i = e[l]) && (t = i(t) || t);
  return t;
};
const n = window.Vaadin.devTools;
let p = class extends $ {
  render() {
    return r` <style>
        ${x}
      </style>
      ${d.featureFlags.map(
      (e) => r`
          <copilot-toggle-button
            .title="${e.title}"
            ?checked=${e.enabled}
            @on-change=${(a) => this.toggleFeatureFlag(a, e)}>
          </copilot-toggle-button>
          <a class="ahreflike" href="${e.moreInfoLink}" title="Learn more" target="_blank"
            >learn more ${w.share}</a
          >
        `
    )}`;
  }
  toggleFeatureFlag(e, a) {
    const o = e.target.checked;
    if (c("use-feature", { source: "toggle", enabled: o, id: a.id }), n.frontendConnection) {
      n.frontendConnection.send("setFeature", { featureId: a.id, enabled: o });
      let s;
      if (a.requiresServerRestart) {
        const t = "This feature requires a server restart";
        g() ? s = f(
          r`${t} <br />
              ${u()}`
        ) : s = t;
      }
      h({
        type: m.INFORMATION,
        message: `“${a.title}” ${o ? "enabled" : "disabled"}`,
        details: s,
        dismissId: `feature${a.id}${o ? "Enabled" : "Disabled"}`
      }), v();
    } else
      n.log("error", `Unable to toggle feature ${a.title}: No server connection available`);
  }
};
p = y([
  b("copilot-features-panel")
], p);
const I = {
  header: "Features",
  expanded: !1,
  panelOrder: 35,
  panel: "right",
  floating: !1,
  tag: "copilot-features-panel",
  helpUrl: "https://vaadin.com/docs/latest/flow/configuration/feature-flags"
}, O = {
  init(e) {
    e.addPanel(I);
  }
};
window.Vaadin.copilot.plugins.push(O);
export {
  p as CopilotFeaturesPanel
};
