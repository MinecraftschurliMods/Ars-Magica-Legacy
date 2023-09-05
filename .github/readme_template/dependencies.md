## Dependencies

{% for dependency in dependencies %}
- {% if dependency.curseforge and dependency.modrinth -%}
{{ dependency.name }} ({% link text:"CurseForge" url:dependency.curseforge %} / {% link text:"Modrinth" url:dependency.modrinth %})
{%- elsif dependency.curseforge -%}
{% link text:dependency.name url:dependency.curseforge %}
{%- elsif dependency.modrinth -%}
{% link text:dependency.name url:dependency.modrinth %}
{%- else -%}
{% link text:dependency.name url:dependency.url %}
{%- endif -%}
{% if dependency.required %} (required){% else %} (optional){% endif -%}
{% endfor %}
