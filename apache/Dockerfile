FROM httpd:2.4

RUN sed -i \
    -e 's/^#LoadModule proxy_module/LoadModule proxy_module/' \
    -e 's/^#LoadModule proxy_http_module/LoadModule proxy_http_module/' \
    conf/httpd.conf

COPY proxy.conf conf/extra/proxy.conf

RUN echo "Include conf/extra/proxy.conf" >> conf/httpd.conf