window.initCardTiltEffect = function () {
    function hexToRgb(hex) {
        hex = hex.replace('#', '');
        if (hex.length === 3) {
            hex = hex.split('').map(x => x + x).join('');
        }
        var bigint = parseInt(hex, 16);
        var r = (bigint >> 16) & 255;
        var g = (bigint >> 8) & 255;
        var b = bigint & 255;
        return r + ',' + g + ',' + b;
    }

    setTimeout(function () {
        document.querySelectorAll('.pokemon-card').forEach(function (card) {
            var shine = card.querySelector('.shine');
            var colorHex1 = shine ? shine.getAttribute('data-type-color1') : '#fff';
            var colorHex2 = shine ? shine.getAttribute('data-type-color2') : '#fff';
            var color1 = hexToRgb(colorHex1);
            var color2 = hexToRgb(colorHex2);
            card.addEventListener('mousemove', function (e) {
                var rect = card.getBoundingClientRect();
                var x = e.clientX - rect.left;
                var y = e.clientY - rect.top;
                var centerX = rect.width / 2;
                var centerY = rect.height / 2;
                var rotateX = ((y - centerY) / centerY) * -20;
                var rotateY = ((x - centerX) / centerX) * 20;
                card.style.setProperty('--rX', rotateX + 'deg');
                card.style.setProperty('--rY', rotateY + 'deg');
                card.classList.add('active');
                if (shine) {
                    var angle = Math.atan2(y - centerY, x - centerX) * 180 / Math.PI - 90;
                    var pos = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                    shine.style.background = 'linear-gradient(' + angle + 'deg, rgba(' + color1 + ',' + (0.7 + pos / rect.width * 0.2) + ') 0%, rgba(' + color2 + ',0.5) 80%)';
                }
            });
            card.addEventListener('mouseleave', function () {
                card.style.setProperty('--rX', '0deg');
                card.style.setProperty('--rY', '0deg');
                card.classList.remove('active');
                if (shine) {
                    shine.style.background = '';
                }
            });
        });
    }, 100);
}
