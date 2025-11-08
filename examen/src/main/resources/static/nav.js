(function(){
  function link(href, label){
    const here = location.pathname.replace(/\/index\.html$/,'/');
    const isActive = (href === here) || (href === location.pathname);
    return `<a class="nav-link${isActive?' active':''}" href="${href}">${label}</a>`;
  }
  function render(){
    const html = `
      <header class="site-header">
        <div class="container">
          <div class="brand"><a href="/index.html">Inventario</a></div>
          <nav class="nav">
            ${link('/index.html','Inicio')}
            ${link('/productos.html','Productos')}
            ${link('/proveedores.html','Proveedores')}
            ${link('/bodega.html','Bodega')}
            ${link('/movimientos.html','Movimientos')}
          </nav>
        </div>
      </header>`;
    document.body.insertAdjacentHTML('afterbegin', html);
  }
  if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', render);
  else render();
})();

