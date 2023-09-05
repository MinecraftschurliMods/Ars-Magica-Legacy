<!--suppress HtmlDeprecatedAttribute -->
<div align="center">

{% for badge in badges -%}
    {% if forloop.index == 3 %}</br>{% endif %}
    {% if badge.link -%}
        {%- badge name:badge.name url:badge.url link:badge.link -%}
    {%- else -%}
        {%- badge name:badge.name url:badge.url -%}
    {%- endif -%}
{%- endfor %}

</div>
