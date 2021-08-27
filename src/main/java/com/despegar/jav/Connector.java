package com.despegar.jav;


public interface Connector {

    /**
     * Realiza una busqueda basada en los parametros pasados en @param request
     * @param request
     * @return
     */
    Response search(Request request);

    /**
     * permite navegar por los resultados generados a partir de un search utilizando los parametros de request
     * @param request
     * @return
     */
    Response scroll(Request request);

    /**
     * Libera el scroll de la memoria
     */
    void clearScroll();

}
