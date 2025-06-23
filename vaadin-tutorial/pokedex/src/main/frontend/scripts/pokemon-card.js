window.addEventListener('DOMContentLoaded', () => {
  document.querySelectorAll('.pokemon-card').forEach(card => {
    card.addEventListener('mousemove', (e) => {
      const rect = card.getBoundingClientRect();
      const x = e.clientX - rect.left;
      const y = e.clientY - rect.top;
      const centerX = rect.width / 2;
      const centerY = rect.height / 2;
      const rotateX = ((y - centerY) / centerY) * 10; // 10 degrés max
      const rotateY = ((x - centerX) / centerX) * -10;
      card.style.transform = `scale(1.05) rotateX(${rotateX}deg) rotateY(${rotateY}deg)`;
    });
    card.addEventListener('mouseleave', () => {
      card.style.transform = '';
    });
    card.addEventListener('mouseenter', () => {
      card.style.transition = 'transform 0.15s cubic-bezier(.25,.8,.25,1)';
    });
  });
});